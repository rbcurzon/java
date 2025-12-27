package com.example.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.notes.dto.NoteDTO;
import com.example.notes.entity.Note;
import com.example.notes.service.NoteService;


@Controller
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	NoteService noteService;

	@GetMapping
	public String index(Model model) {
		Note note = noteService.getFirstNote();
		model.addAttribute("note", note);
		model.addAttribute("allNotes", noteService.getAllNotes());
		return "index";
	}

	@GetMapping("/{id}")
	public String getNote(@PathVariable Long id, Model model) {
		model.addAttribute("note", noteService.getNodeContentAsHtml(id));
		model.addAttribute("allNotes", noteService.getAllNotes());
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
}
