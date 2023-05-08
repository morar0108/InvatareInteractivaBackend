package com.example.InvatareInteractivaBackend.service;

import com.example.InvatareInteractivaBackend.model.StickyNote;
import com.example.InvatareInteractivaBackend.repository.StickyNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StickyNoteService {
    @Autowired
    private StickyNoteRepository stickyNoteRepository;

    public List<StickyNote> getAllStickyNotes() {
        return stickyNoteRepository.findAll();
    }

    public Optional<StickyNote> getStickyNoteById(Long id) {
        return stickyNoteRepository.findById(id);
    }

    public StickyNote saveStickyNote(StickyNote stickyNote) {
        return stickyNoteRepository.save(stickyNote);
    }

    public void deleteStickyNoteById(Long id) {
        stickyNoteRepository.deleteById(id);
    }
}
