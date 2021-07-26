package com.helloword.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/hello")
public class Ola_mundaoController {
	
	@GetMapping
	public String hello() {
		return "Olá mundão";
	}
}
