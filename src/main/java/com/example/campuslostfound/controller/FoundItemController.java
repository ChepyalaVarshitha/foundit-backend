package com.example.campuslostfound.controller;

import com.example.campuslostfound.model.FoundItem;
import com.example.campuslostfound.repository.FoundItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/found-items")
@CrossOrigin(origins = "http://localhost:5173")
public class FoundItemController {
    
    @Autowired
    private FoundItemRepository foundItemRepository;
    
    // GET all unclaimed found items
    @GetMapping
    public List<FoundItem> getAllFoundItems() {
        return foundItemRepository.findByClaimedFalse();
    }
    
    // GET single found item
    @GetMapping("/{id}")
    public ResponseEntity<FoundItem> getFoundItemById(@PathVariable Long id) {
        return foundItemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create new found item
    @PostMapping
    public ResponseEntity<?> createFoundItem(@RequestBody Map<String, Object> payload) {
        try {
            FoundItem foundItem = new FoundItem();
            
            // Map fields from React form
            foundItem.setTitle((String) payload.get("title"));
            foundItem.setDescription((String) payload.get("description"));
            foundItem.setLocation((String) payload.get("location"));
            foundItem.setContactName((String) payload.get("name"));
            foundItem.setPhoneNumber((String) payload.get("phone"));
            
            // Handle date
            String dateStr = (String) payload.get("date_found");
            if (dateStr != null && !dateStr.isEmpty()) {
                foundItem.setDate(LocalDateTime.parse(dateStr + "T00:00:00"));
            } else {
                foundItem.setDate(LocalDateTime.now());
            }
            
            // Set defaults
            foundItem.setClaimed(false);
            foundItem.setCreatedAt(LocalDateTime.now());
            foundItem.setImage(null);
            
            FoundItem savedItem = foundItemRepository.save(foundItem);
            return ResponseEntity.ok(savedItem);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create found item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // PUT claim found item
    @PutMapping("/{id}/claim")
    public ResponseEntity<?> claimFoundItem(@PathVariable Long id) {
        return foundItemRepository.findById(id)
                .map(item -> {
                    item.setClaimed(true);
                    foundItemRepository.save(item);
                    
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Item claimed successfully");
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // GET search found items
    @GetMapping("/search")
    public List<FoundItem> searchFoundItems(@RequestParam String q) {
        return foundItemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndClaimedFalse(q, q);
    }
}