package com.example.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class NoteController {

	@Autowired
	NoteService noteService;

	@GetMapping("/")
	public String getAllNotes(Model model) {
		model.addAttribute("allNotes", noteService.getAllNotes());
		return "index";
	}

	@GetMapping("/addNew")
	public String addNote(Model model) {
		Note note = new Note();
		model.addAttribute("noteModel", note);
		return "addNew";
	}

	@PostMapping("/save")
	public String createNote(@ModelAttribute("noteModel") Note note) {
		noteService.createNote(note);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteNote(@PathVariable long id) {
		noteService.deleteNote(id);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable long id, Model model) {
		Note note = noteService.getNoteById(id);
		model.addAttribute("note", note);
		return "edit";
	}

	@PostMapping("/update/{id}")
	public String updateNote(@PathVariable("id") long id, @Valid @ModelAttribute("noteModel") Note note,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			note.setId(id);
			return "edit";
		}

		noteService.updateNote(id, note);
		return "redirect:/";
	}
}
