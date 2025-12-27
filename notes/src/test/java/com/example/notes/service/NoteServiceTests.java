package com.example.notes.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.notes.entity.Note;
import com.example.notes.repository.NoteRepository;

@SpringBootTest
public class NoteServiceTests {

	@MockitoBean
	NoteRepository noteRepository;

	@Autowired
	NoteService noteService;

	Note note;

	@BeforeEach
	void setUp() {
		note = new Note("Oscar Wilde", "*Be yourself*; everyone else is already taken.");
	}

	@Test
	void whenGetFirstNote_thenReturnFirstNote() {
		Mockito.when(noteRepository.findFirstByOrderByUpdatedAtDesc()).thenReturn(note);

		Note noteResult = noteService.getFirstNote();

		String expected = note.getContent();
		String result = noteResult.getContent();

		assertThat(result).isEqualTo(expected);
	}

}
