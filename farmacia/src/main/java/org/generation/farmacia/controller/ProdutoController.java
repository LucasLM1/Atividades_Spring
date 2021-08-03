package org.generation.farmacia.controller;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.generation.farmacia.model.Categoria;
import org.generation.farmacia.model.Produto;
import org.generation.farmacia.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Entity
@Table(name = "tb_produto")
@CrossOrigin(value = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(repository.findAll());

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> GetById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
}
