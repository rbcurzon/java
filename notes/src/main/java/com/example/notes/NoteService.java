package com.example.notes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;

	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}

	public Note getNoteById(Long id) {
		return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	}

	public Note createNote(Note note) {
		return noteRepository.save(note);
	}

	public Note updateNote(Long id, Note note) {
		Note existingNote = noteRepository.findById(id).orElse(null);
		if (existingNote != null) {
			existingNote.setTitle(note.getTitle());
			existingNote.setNote(note.getNote());
			return noteRepository.save(existingNote);
		} else {
			return null;
		}
	}

	public void deleteNote(Long id) {
		noteRepository.deleteById(id);
	}
}