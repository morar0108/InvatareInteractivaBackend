package com.example.InvatareInteractivaBackend.repository;

import com.example.InvatareInteractivaBackend.model.StickyNote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StickyNoteRepository extends CrudRepository<StickyNote, Long> {
    // You can add custom query methods here if needed
    List<StickyNote> findByTitle(String title);

    // Find all StickyNotes for a given category ID
    List<StickyNote> findByCategoryId(Long categoryId);

    // Find all StickyNotes that contain the specified keyword in either the title or description
    List<StickyNote> findByTitleContainingOrDescriptionContaining(String keyword, String keyword2);

    // Find all StickyNotes for a given user ID
    List<StickyNote> findByCategoryUser_Id(Long userId);

    // Find all StickyNotes sorted by title in ascending order
    List<StickyNote> findAllByOrderByTitleAsc();

    // Find the first StickyNote for a given category ID
    StickyNote findFirstByCategoryIdOrderByTitleAsc(Long categoryId);

    List<StickyNote> findAll();


}

