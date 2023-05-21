package com.example.InvatareInteractivaBackend.controller;

import com.example.InvatareInteractivaBackend.DTO.StickyDTO;
import com.example.InvatareInteractivaBackend.model.Category;
import com.example.InvatareInteractivaBackend.model.StickyNote;
import com.example.InvatareInteractivaBackend.service.CategoryService;
import com.example.InvatareInteractivaBackend.service.StickyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", originPatterns = "*", allowedHeaders = "*")
@RestController
public class StickyNoteController {

    @Autowired
    private StickyNoteService stickyNoteService;

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin("*")
    @GetMapping("/sticky-notes")
    public ResponseEntity<List<StickyNote>> getAllStickyNotes() {
        List<StickyNote> stickyNotes = stickyNoteService.getAllStickyNotes();
        return new ResponseEntity<>(stickyNotes, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("/{id}")
    public ResponseEntity<StickyDTO> getStickyNoteById(@PathVariable Long id) {
        Optional<StickyNote> optionalStickyNote = stickyNoteService.getStickyNoteById(id);
        if (optionalStickyNote.isPresent()) {
            StickyNote stickyNote = optionalStickyNote.get();
            Optional<Category> optionalCategory = categoryService.getCategoryById(stickyNote.getCategory().getId());
            if(optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                StickyDTO stickyDTO = new StickyDTO();
                stickyDTO.setId(stickyNote.getId());
                stickyDTO.setTitle(stickyNote.getTitle());
                stickyDTO.setDescription(stickyNote.getDescription());
                stickyDTO.setCategoryName(category.getName());
                return new ResponseEntity<>(stickyDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin("*")
    @PostMapping("/saveStickyNote")
    public ResponseEntity<StickyNote> saveStickyNote(@RequestBody StickyNote stickyNote) {
        StickyNote savedStickyNote = stickyNoteService.saveStickyNote(stickyNote);
        return new ResponseEntity<>(savedStickyNote, HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @PutMapping("/{id}")
    public ResponseEntity<StickyNote> updateStickyNote(@PathVariable Long id, @RequestBody StickyNote stickyNote) {
        Optional<StickyNote> existingStickyNote = stickyNoteService.getStickyNoteById(id);
        if (existingStickyNote.isPresent()) {
            stickyNote.setId(id);
            StickyNote savedStickyNote = stickyNoteService.saveStickyNote(stickyNote);
            return new ResponseEntity<>(savedStickyNote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin("*")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStickyNoteById(@PathVariable Long id) {
        Optional<StickyNote> existingStickyNote = stickyNoteService.getStickyNoteById(id);
        if (existingStickyNote.isPresent()) {
            stickyNoteService.deleteStickyNoteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

