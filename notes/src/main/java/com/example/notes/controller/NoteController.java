package com.example.notes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.notes.dto.NoteDTO;
import com.example.notes.entity.Note;
import com.example.notes.repository.NoteRepository;
import com.example.notes.service.MarkdownService;
import com.example.notes.service.NoteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {
	private final NoteRepository noteRepository;

	@Autowired
	NoteService noteService;

	@Autowired
	MarkdownService markdownService;

	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	NoteController(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	@GetMapping({ "", "/{id}" })
	public String index(@PathVariable(required = false) Long id, Model model) {

		model.addAttribute("allNotes", noteService.getAllNotesByOrder());

		if (id == null) {
			logger.info("Variable id is null");
			return "index";
		}
		
		Note note = noteService.getNoteById(id);

		String htmlContent = markdownService.convertMarkdownToHtml(note.getContent());

		model.addAttribute("note", note);
		model.addAttribute("htmlContent", htmlContent);
		
		return "index";
	}

	@GetMapping("/addNew")
	public String addNote(Model model) {
		Note note = new Note();
		model.addAttribute("note", note);
		return "addNew";
	}

	@PostMapping("/save")
	public String createNote(@ModelAttribute("note") NoteDTO noteDTO) {
		noteService.createNote(noteDTO);
		return "redirect:/notes";
	}

	@GetMapping("/delete/{id}")
	public String deleteNote(@PathVariable("id") long id) {
		noteService.deleteNote(id);
		return "redirect:/notes";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable long id, Model model) {
		Note note = noteService.getNoteById(id);
		model.addAttribute("note", note);
		return "edit";
	}

	@PostMapping("/update/{id}")
	public String saveNote(@PathVariable("id") long id, @Valid Note note, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			note.setId(id);
			return "edit";
		}

		noteRepository.save(note);
		return "redirect:/notes";
	}
}
