package org.luizinfo.controller;

import java.util.List;
import java.util.Optional;

import org.luizinfo.model.Media;
import org.luizinfo.repository.IMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Media", description = "Métodos do Controller de Medias")
@RequestMapping(value = "/media")
public class MediaController implements CrudController<Media> {
	
	@Autowired
	private IMedia iMedia;

	@Override
	public ResponseEntity<?> listar() {
		List<Media> medias = iMedia.findAll();
		
		if (medias.size() > 0) {
			return new ResponseEntity<List<Media>>(medias, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Não foram encontrados registros cadastrados!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> inserir(@RequestBody Media objeto, 
			@RequestHeader(name="Authorization") String token) {
		
		if (objeto.getId() != null) {
			return new ResponseEntity<String>("ID do Registro não deve ser Informado para cadastrar!", HttpStatus.BAD_REQUEST);
		}
		
		Media mediaAux = iMedia.save(objeto);
		
		return new ResponseEntity<Media>(mediaAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> atualizar(@RequestBody Media objeto,
			@RequestHeader(name="Authorization") String token) {
		
		if (objeto.getId() == null) {
			return new ResponseEntity<String>("ID do Registro não foi Informado para atualizar!", HttpStatus.BAD_REQUEST);
		}

		Media mediaAux = iMedia.save(objeto);
		
		return new ResponseEntity<Media>(mediaAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> excluir(Long id, String token) {
		Optional<Media> mediaOp = iMedia.findById(id);
		
		if (mediaOp.isPresent()) {
			iMedia.delete(mediaOp.get());
			return new ResponseEntity<String>("Media Excluída com Sucesso!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Media não Encontrada com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> localizar(Long id) {
		Optional<Media> mediaOp = iMedia.findById(id);
		
		if (mediaOp.isPresent()) {
			return new ResponseEntity<Media>(mediaOp.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Media não Encontrada com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

}
