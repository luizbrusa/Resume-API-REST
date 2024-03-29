package org.luizinfo.controller;

import java.util.List;
import java.util.Optional;

import org.luizinfo.model.Internationalization;
import org.luizinfo.model.Pessoa;
import org.luizinfo.model.Post;
import org.luizinfo.repository.IPessoa;
import org.luizinfo.repository.IPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Posts", description = "Métodos do Controller de Posts")
@RequestMapping(value = "/post")
public class PostController implements CrudController<Post> {
	
	@Autowired
	private IPost iPost;
	
	@Autowired
	private IPessoa iPessoa;

	@Override
	public ResponseEntity<?> listar() {
		
		List<Post> posts = iPost.findAll();
		
		if (posts.size() > 0) {
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Não foram encontrados Posts cadastrados!",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> inserir(Post objeto, @RequestHeader(name="Authorization") String token) {
		
		if (objeto.getId() != null) {
			return new ResponseEntity<String>("ID do Post não deve ser Informado para cadastrar!", HttpStatus.BAD_REQUEST);
		}
		if (objeto.getInternationalizations() != null) {
			for (Internationalization internationalization : objeto.getInternationalizations()) {
				internationalization.setPost(objeto);
				internationalization.setPessoa(null);
				internationalization.setExperience(null);
			}
		}
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(objeto.getDate());
//		calendar.add(Calendar.DATE, 1);
//		objeto.setDate(calendar.getTime());
		
		iPost.save(objeto);
		
		return new ResponseEntity<Post>(objeto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> atualizar(Post objeto, @RequestHeader(name="Authorization") String token) {

		if (objeto.getId() == null) {
			return new ResponseEntity<String>("ID do Post não foi Informado para atualizar!", HttpStatus.BAD_REQUEST);
		}
		if (objeto.getInternationalizations().size() > 0) {
			for (Internationalization internationalization : objeto.getInternationalizations()) {
				internationalization.setPost(objeto);
				internationalization.setPessoa(null);
				internationalization.setExperience(null);
			}
		}
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(objeto.getDate());
//		calendar.add(Calendar.DATE, 1);
//		objeto.setDate(calendar.getTime());

		Post postAux = iPost.save(objeto);
		
		return new ResponseEntity<Post>(postAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> excluir(Long id, @RequestHeader(name="Authorization") String token) {
		Optional<Post> postOp = iPost.findById(id);
		
		if (postOp.isPresent()) {
			iPost.delete(postOp.get());
			return new ResponseEntity<String>("Post Excluído com Sucesso!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Post não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> localizar(Long id) {
		Optional<Post> postOp = iPost.findById(id);
		
		if (postOp.isPresent()) {
			return new ResponseEntity<Post>(postOp.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Post não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(description = "Localizar Posts de uma Pessoa")
	@GetMapping(value = "/pessoa/{id}", produces = "application/json")
	public ResponseEntity<?> localizarPostsPessoa(@PathVariable(value = "id") Long id) {
		Optional<Pessoa> pessoaOp = iPessoa.findById(id);
		
		if (pessoaOp.isPresent()) {
			List<Post> posts = iPost.findByPessoa(pessoaOp.get());
			return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Pessoa Não Localizada com o ID informado", HttpStatus.BAD_REQUEST);
		}
	}

}
