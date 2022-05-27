package org.luizinfo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import io.swagger.annotations.ApiOperation;

public interface CrudController<T> {

	@ApiOperation(value = "Listar todos os Registros Cadastrados")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listar();
	
	@ApiOperation(value = "Cadastrar um Registro")
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inserir(@RequestBody T objeto, @RequestHeader(name="Authorization") String token);
	
	@ApiOperation(value = "Atualizar um Registro")
	@PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> atualizar(@RequestBody T objeto, @RequestHeader(name="Authorization") String token);

	@ApiOperation(value = "Excluir um Registro")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id, @RequestHeader(name="Authorization") String token);

	@ApiOperation(value = "Localizar um Registro por ID")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> localizar(@PathVariable(value = "id") Long id);

}
