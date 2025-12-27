package com.example.notes.service;

import java.util.List;
import java.util.Optional;

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

	private MarkdownService markdownService = new MarkdownService();

	public Note getFirstNote() {
		return noteRepository.findFirstByOrderByCreatedAtDesc();
	}
	
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}

	public Note getNoteById(Long id) {
		return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	}

	public Note getNodeContentAsHtml(Long id) {
		Note note = getNoteById(id);
		note.setContent(markdownService.convertMarkdownToHtml(note.getContent()));
		return note;
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