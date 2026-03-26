package com.example.campuslostfound.controller;

import com.example.campuslostfound.model.LostItem;
import com.example.campuslostfound.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/lost-items")
public class LostItemController {
    
    @Autowired
    private LostItemRepository lostItemRepository;
    
    // GET all lost items (only unclaimed ones)
    @GetMapping
    public List<LostItem> getAllLostItems() {
        return lostItemRepository.findByClaimedFalse();
    }
    
    // GET a single lost item by id
    @GetMapping("/{id}")
    public ResponseEntity<LostItem> getLostItemById(@PathVariable Long id) {
        return lostItemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create a new lost item
    @PostMapping
    public ResponseEntity<?> createLostItem(@RequestBody Map<String, Object> payload) {
        try {
            LostItem lostItem = new LostItem();
            
            // Map basic fields from React form
            lostItem.setTitle((String) payload.get("title"));
            lostItem.setLocation((String) payload.get("location"));
            
            // Combine description with contact info
            String description = (String) payload.get("description");
            String contactName = (String) payload.get("name");
            String phone = (String) payload.get("phone");
            
            String fullDescription = String.format("%s\n\nContact Person: %s\nPhone: %s", 
                description != null ? description : "", 
                contactName != null ? contactName : "", 
                phone != null ? phone : "");
            lostItem.setDescription(fullDescription);
            
            // Handle date
            String dateStr = (String) payload.get("date_lost");
            if (dateStr != null && !dateStr.isEmpty()) {
                lostItem.setDate(LocalDateTime.parse(dateStr + "T00:00:00"));
            } else {
                lostItem.setDate(LocalDateTime.now());
            }
            
            // Set defaults
            lostItem.setClaimed(false);
            lostItem.setCreatedAt(LocalDateTime.now());
            lostItem.setImage(null);
            
            // Save to database
            LostItem savedItem = lostItemRepository.save(lostItem);
            
            return ResponseEntity.ok(savedItem);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create lost item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // PUT mark item as claimed
    @PutMapping("/{id}/claim")
    public ResponseEntity<LostItem> claimLostItem(@PathVariable Long id) {
        return lostItemRepository.findById(id)
                .map(item -> {
                    item.setClaimed(true);
                    return ResponseEntity.ok(lostItemRepository.save(item));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // GET search items
    @GetMapping("/search")
    public List<LostItem> searchLostItems(@RequestParam String q) {
        return lostItemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndClaimedFalse(q, q);
    }
}