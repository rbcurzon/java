package com.example.notes.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteResponseDTO {
	private String title;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
