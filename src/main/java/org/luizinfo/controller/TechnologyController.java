package org.luizinfo.controller;

import java.util.List;
import java.util.Optional;

import org.luizinfo.model.Technology;
import org.luizinfo.repository.ITechnology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(tags = "Métodos do Controller de Experiences")
@RequestMapping(value = "/technology")
public class TechnologyController implements CrudController<Technology> {

	@Autowired
	private ITechnology iTechnology;

	@Override
	public ResponseEntity<?> listar() {
		List<Technology> technologies = iTechnology.findAll();
		if (technologies.size() > 0) {
			return new ResponseEntity<List<Technology>>(technologies, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Não foram encontrados Registros cadastrados!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> inserir(Technology objeto, String token) {
		if (objeto.getId() != null) {
			return new ResponseEntity<String>("ID do Registro não deve ser Informado para cadastrar!", HttpStatus.BAD_REQUEST);
		}
		
		iTechnology.save(objeto);
		
		return new ResponseEntity<Technology>(objeto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> atualizar(Technology objeto, String token) {
		if (objeto.getId() == null) {
			return new ResponseEntity<String>("ID do Registro não foi Informado para atualizar!", HttpStatus.BAD_REQUEST);
		}
		Technology technologyAux = iTechnology.save(objeto);
		
		return new ResponseEntity<Technology>(technologyAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> excluir(Long id, String token) {
		Optional<Technology> technologyOp = iTechnology.findById(id);
		
		if (technologyOp.isPresent()) {
			iTechnology.delete(technologyOp.get());
			return new ResponseEntity<String>("Registro Excluído com Sucesso!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Registro não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> localizar(Long id) {
		Optional<Technology> technologyOp = iTechnology.findById(id);
		
		if (technologyOp.isPresent()) {
			return new ResponseEntity<Technology>(technologyOp.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Post não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

}
