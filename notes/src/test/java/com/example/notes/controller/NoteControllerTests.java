package com.example.notes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.example.notes.controller.NoteController;
import com.example.notes.service.NoteService;

import jakarta.annotation.PostConstruct;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTests {

	@Autowired
	MockMvc mockMvc;

	MockMvcTester mockMvcTester;

	@PostConstruct
	void setUp() {
		mockMvcTester = MockMvcTester.create(mockMvc);
	}

	@Test
	void whenAddNote_thenExistInRepository() throws Exception {
		var mockMvcTesterResult = mockMvcTester.post().uri("/notes/save").param("title", "test_title")
				.param("content", "test_content") // Ensure 'note' matches your @ModelAttribute field name
				.exchange();

		assertThat(mockMvcTesterResult).hasStatus(HttpStatus.FOUND).hasRedirectedUrl("/notes");
	}
}
