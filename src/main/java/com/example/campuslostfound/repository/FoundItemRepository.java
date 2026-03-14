package com.example.campuslostfound.repository;

import com.example.campuslostfound.model.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
    List<FoundItem> findByClaimedFalse();
    List<FoundItem> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndClaimedFalse(
        String titleKeyword, String descriptionKeyword);
}