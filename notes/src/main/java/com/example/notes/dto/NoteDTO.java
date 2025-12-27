package com.example.notes.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class NoteDTO {
	private String title;
	private String content;
}