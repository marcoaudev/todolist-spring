package br.com.marcoaurelio.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@-anotacion, toda anotacion ela tem uma função para ser executada;
@SpringBootApplication
//Define que essa classe é a inicial
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
		// Por baixo dos panos, tem um toncat, um container, para executar nossa aplicação;
	}
}
