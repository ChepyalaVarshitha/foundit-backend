package com.example.campuslostfound.repository;

import com.example.campuslostfound.model.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    // Find items that are not claimed
    List<LostItem> findByClaimedFalse();
    
    // Search by title or description (for search feature)
    List<LostItem> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndClaimedFalse(
        String titleKeyword, String descriptionKeyword);
}