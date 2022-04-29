package org.luizinfo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Index")
@RestController
public class IndexController {

	@ApiOperation(value = "Index de Testes da Aplicação")
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<String> index() {
		return new ResponseEntity<String>("Index Resume - Teste da Aplicação \n\nAcesse a API no endereço: http://localhost:8080/resume/swagger-ui/index.html", HttpStatus.OK);
	}

}
