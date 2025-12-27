package com.example.notes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import jakarta.annotation.PostConstruct;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTests {

	@Autowired
	MockMvc mockMvc;
	MockMvcTester mockMvcTester;

	@PostConstruct
	void setUp() {
		mockMvcTester = MockMvcTester.create(mockMvc);
	}

	@Test
	public void givenNotes_whenGetIndex_thenSuccess() throws Exception {
		var result = mockMvcTester.get().uri("/notes").exchange();

		assertThat(result).hasStatusOk().hasViewName("index");
	}

	@Test
	public void shouldCreateNoteSuccessfully() {
		var result = mockMvcTester.post().uri("/notes/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "test title").param("content", "test note").exchange();

		assertThat(result).hasStatus(HttpStatus.FOUND).hasRedirectedUrl("/notes");
	}

	@Test
	public void shouldDeleteNoteSuccessfully() {

		var result = mockMvcTester.get().uri("/notes/delete/1").exchange();
		assertThat(result).hasStatus(HttpStatus.FOUND).hasRedirectedUrl("/notes");
	}

}
