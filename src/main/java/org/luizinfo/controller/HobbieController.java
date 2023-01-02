package org.luizinfo.controller;

import java.util.List;
import java.util.Optional;

import org.luizinfo.model.Hobbie;
import org.luizinfo.repository.IHobbie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Hobbie", description = "Métodos do Controller de Hobbies")
@RequestMapping(value = "/hobbie")
public class HobbieController implements CrudController<Hobbie> {

	@Autowired
	private IHobbie iHobbie;

	@Override
	public ResponseEntity<?> listar() {
		
		List<Hobbie> hobbies = iHobbie.findAll();
		
		if (hobbies.size() > 0) {
			return new ResponseEntity<List<Hobbie>>(hobbies, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Não foram encontrados registros cadastrados!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> inserir(Hobbie objeto, String token) {

		if (objeto.getId() != null) {
			return new ResponseEntity<String>("ID do Registro não deve ser Informado para cadastrar!", HttpStatus.BAD_REQUEST);
		}
		
		Hobbie hobbieAux = iHobbie.save(objeto);
		
		return new ResponseEntity<Hobbie>(hobbieAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> atualizar(Hobbie objeto, String token) {
		
		if (objeto.getId() == null) {
			return new ResponseEntity<String>("ID do Registro não foi Informado para atualizar!", HttpStatus.BAD_REQUEST);
		}

		Hobbie hobbieAux = iHobbie.save(objeto);
		
		return new ResponseEntity<Hobbie>(hobbieAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> excluir(Long id, String token) {

		Optional<Hobbie> hobbieOp = iHobbie.findById(id);

		if (hobbieOp.isPresent()) {
			iHobbie.delete(hobbieOp.get());
			return new ResponseEntity<String>("Registro Excluído com Sucesso!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Registro não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> localizar(Long id) {

		Optional<Hobbie> hobbieOp = iHobbie.findById(id);
		
		if (hobbieOp.isPresent()) {
			return new ResponseEntity<Hobbie>(hobbieOp.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Registro não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

}
