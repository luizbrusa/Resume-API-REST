package org.luizinfo.controller;

import java.util.List;
import java.util.Optional;

import org.luizinfo.model.Experience;
import org.luizinfo.model.Internationalization;
import org.luizinfo.model.Media;
import org.luizinfo.model.Pessoa;
import org.luizinfo.model.Technology;
import org.luizinfo.repository.IExperience;
import org.luizinfo.repository.IPessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Métodos do Controller de Experiences")
@RequestMapping(value = "/experience")
public class ExperienceController implements CrudController<Experience> {

	@Autowired
	private IExperience iExperience;
	
	@Autowired
	private IPessoa iPessoa;
	
	@Override
	public ResponseEntity<?> listar() {
		List<Experience> experiences = iExperience.findAll();
		if (experiences.size() > 0) {
			return new ResponseEntity<List<Experience>>(experiences, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Não foram encontrados Registros cadastrados!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> inserir(Experience objeto, @RequestHeader(name="Authorization") String token) {
		if (objeto.getId() != null) {
			return new ResponseEntity<String>("ID do Registro não deve ser Informado para cadastrar!", HttpStatus.BAD_REQUEST);
		}
		for (Internationalization internationalization : objeto.getInternationalizations()) {
			internationalization.setExperience(objeto);
		}
		for (Technology technology : objeto.getTechnologies()) {
			technology.setExperience(objeto);
		}
		for (Media media : objeto.getMedias()) {
			media.setExperience(objeto);
		}
		iExperience.save(objeto);
		
		return new ResponseEntity<Experience>(objeto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> atualizar(Experience objeto, @RequestHeader(name="Authorization") String token) {
		if (objeto.getId() == null) {
			return new ResponseEntity<String>("ID do Registro não foi Informado para atualizar!", HttpStatus.BAD_REQUEST);
		}
		Experience experienceAux = iExperience.save(objeto);
		
		return new ResponseEntity<Experience>(experienceAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> excluir(Long id, @RequestHeader(name="Authorization") String token) {
		Optional<Experience> experienceOp = iExperience.findById(id);
		
		if (experienceOp.isPresent()) {
			iExperience.delete(experienceOp.get());
			return new ResponseEntity<String>("Registro Excluído com Sucesso!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Registro não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> localizar(Long id) {
		Optional<Experience> experienceOp = iExperience.findById(id);
		
		if (experienceOp.isPresent()) {
			return new ResponseEntity<Experience>(experienceOp.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Post não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Localizar Experiencias de uma Pessoa")
	@GetMapping(value = "/pessoa/{id}", produces = "application/json")
	public ResponseEntity<?> localizarExperiencesPessoa(@PathVariable(value = "id") Long id) {
		Optional<Pessoa> pessoaOp = iPessoa.findById(id);
		
		if (pessoaOp.isPresent()) {
			List<Experience> experiences = iExperience.findByPessoa(pessoaOp.get());
			return new ResponseEntity<List<Experience>>(experiences,HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Pessoa Não Localizada com o ID informado", HttpStatus.BAD_REQUEST);
		}
	}

}
