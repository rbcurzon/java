package com.example.notes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.example.notes.entity.Note;
import com.example.notes.repository.NoteRepository;

import jakarta.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
public class MarkdownServiceTests {

	@MockitoBean
	private NoteRepository noteRepository;

	@Autowired
	MarkdownService markdownService;

	String markdown = "This is *Markdown*";
	Note note;

	@BeforeEach
	void setUp() {
		note = new Note("Oscar Wilde", "*Be yourself*; everyone else is already taken.");
	}

	@Test
	public void givenNoteContent_whenGetContentAsHtml_thenReturnInHtml() {
		long id = 0;
		Mockito.when(noteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(note));
		Optional<Note> note = noteRepository.findById(id);
		
		assertThat(note).isNotNull();
		
		String result = markdownService.convertMarkdownToHtml(note.get().getContent()).strip();
		String expected = "<p><em>Be yourself</em>; everyone else is already taken.</p>";
		
		assertThat(result).isEqualTo(expected);
	}

	@Test
	public void givenMarkdown_whenGetContentAsHtml_thenReturnHtml() {
		String result = markdownService.convertMarkdownToHtml(markdown).strip();
		String expected = "<p>This is <em>Markdown</em></p>";

		assertThat(result).isEqualTo(expected);
	}

}
