package com.example.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.notes.dto.NoteDTO;
import com.example.notes.dto.NoteResponseDTO;
import com.example.notes.entity.Note;
import com.example.notes.repository.NoteRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;

	private ModelMapper modelMapper = new ModelMapper();

	public Note getFirstNote() {
		return noteRepository.findFirstByOrderByUpdatedAtDesc();
	}

	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}

	public List<Note> getAllNotesByOrder() {
		return noteRepository.findAllByOrderByUpdatedAtDesc();
	}
	
	public Note getNoteById(Long id) {
		return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	}

	public NoteResponseDTO createNote(NoteDTO noteDTO) {
		Note note = modelMapper.map(noteDTO, Note.class);
		NoteResponseDTO noteResponseDTO = modelMapper.map(noteDTO, NoteResponseDTO.class);
		noteRepository.save(note);
		return noteResponseDTO;
	}

	public void deleteNote(Long id) {
		noteRepository.deleteById(id);
	}

}