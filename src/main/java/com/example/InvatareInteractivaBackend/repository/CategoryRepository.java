package com.example.InvatareInteractivaBackend.repository;

import com.example.InvatareInteractivaBackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can add custom query methods here if needed

    // Find all categories for a given user ID
    List<Category> findByUserId(Long userId);

    // Find all categories that contain the specified keyword in the name
    List<Category> findByNameContaining(String keyword);

    // Find all categories sorted by name in ascending order
    List<Category> findAllByOrderByNameAsc();

    // Find the category with the most sticky notes
    @Query("SELECT c FROM Category c JOIN c.stickyNotes s GROUP BY c.id ORDER BY COUNT(s) DESC")
    Category findCategoryWithMostStickyNotes();

    @Query(value = "select * from invatare_interactiva_db.categories u where u.name=?", nativeQuery = true)
    Category findByName(@Param("name") String name);

}

