package org.luizinfo.controller;

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
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<?> listar();

	@ApiOperation(value = "Cadastrar um Registro")
	@PostMapping(value = "/", produces = "application/json", consumes="application/json")
	public ResponseEntity<?> inserir(@RequestBody T objeto, @RequestHeader(name="Authorization") String token);
	
	@ApiOperation(value = "Atualizar um Registro")
	@PutMapping(value = "/", produces = "application/json", consumes="application/json")
	public ResponseEntity<?> atualizar(@RequestBody T objeto, @RequestHeader(name="Authorization") String token);
	
	@ApiOperation(value = "Excluir um Registro")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id, @RequestHeader(name="Authorization") String token);
	
	@ApiOperation(value = "Localizar um Registro por ID")
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> localizar(@PathVariable(value = "id") Long id);

}
