package com.example.notes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.notes.entity.Note;
import com.example.notes.repository.NoteRepository;

@SpringBootApplication
public class NotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

    @Bean
    CommandLineRunner loadData(NoteRepository repository) {
		return (args) -> {
			repository.save(new Note("Oscar Wilde","*Be yourself*; everyone else is already taken."));
			repository.save(new Note("Frank Zappa", "So many books, *so little time.*"));
			repository.save(new Note("Marcus Tulius Cicero", "A room without books is like a body without a soul."));
		};
		
	}

}
