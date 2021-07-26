package com.helloword.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/mentalidade")
public class MentalidadeController {
	
	@GetMapping
	public String mentalidades() {
		return "Mentalidades:\n"
				+ "Orientação ao detalhe\n"
				+ "Mentalidade de persistencia\n"
				+ "Orientação ao futuro";
	}
}
