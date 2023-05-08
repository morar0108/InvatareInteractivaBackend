package com.example.InvatareInteractivaBackend.controller;

import com.example.InvatareInteractivaBackend.model.StickyNote;
import com.example.InvatareInteractivaBackend.service.StickyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sticky-notes")
public class StickyNoteController {

    @Autowired
    private StickyNoteService stickyNoteService;

    @GetMapping
    public ResponseEntity<List<StickyNote>> getAllStickyNotes() {
        List<StickyNote> stickyNotes = stickyNoteService.getAllStickyNotes();
        return new ResponseEntity<>(stickyNotes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickyNote> getStickyNoteById(@PathVariable Long id) {
        Optional<StickyNote> stickyNote = stickyNoteService.getStickyNoteById(id);
        if (stickyNote.isPresent()) {
            return new ResponseEntity<>(stickyNote.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<StickyNote> saveStickyNote(@RequestBody StickyNote stickyNote) {
        StickyNote savedStickyNote = stickyNoteService.saveStickyNote(stickyNote);
        return new ResponseEntity<>(savedStickyNote, HttpStatus.CREATED);
    }

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
