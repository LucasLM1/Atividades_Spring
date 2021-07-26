package com.helloword.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/agenda")
public class AgendaSemanal {
	
	@GetMapping
	public String agenda() {
		return "Agenda da semana:\n"
				+ "Focar mais nos meus estudos\n"
				+ "Ter mais paciencia\n"
				+ "Tentar cobrar menos de mim\n"
				+ "Te reunir com o grupo do projeto integrador";
	}
}
