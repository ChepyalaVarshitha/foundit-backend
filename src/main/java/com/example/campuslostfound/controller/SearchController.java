package com.example.campuslostfound.controller;

import com.example.campuslostfound.model.LostItem;
import com.example.campuslostfound.model.FoundItem;
import com.example.campuslostfound.repository.LostItemRepository;
import com.example.campuslostfound.repository.FoundItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private LostItemRepository lostItemRepository;

    @Autowired
    private FoundItemRepository foundItemRepository;

    @GetMapping
    public List<Map<String, Object>> search(@RequestParam String q) {
        List<Map<String, Object>> results = new ArrayList<>();

        // Search lost items
        List<LostItem> lostItems = lostItemRepository
            .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndClaimedFalse(q, q);
        
        for (LostItem item : lostItems) {
            Map<String, Object> result = new HashMap<>();
            result.put("id", item.getId());
            result.put("title", item.getTitle());
            result.put("description", item.getDescription());
            result.put("type", "lost");
            results.add(result);
        }

        // Search found items
        List<FoundItem> foundItems = foundItemRepository
            .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndClaimedFalse(q, q);
        
        for (FoundItem item : foundItems) {
            Map<String, Object> result = new HashMap<>();
            result.put("id", item.getId());
            result.put("title", item.getTitle());
            result.put("description", item.getDescription());
            result.put("type", "found");
            results.add(result);
        }

        return results;
    }
}