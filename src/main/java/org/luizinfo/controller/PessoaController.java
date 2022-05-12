package org.luizinfo.controller;

import java.util.List;
import java.util.Optional;

import org.luizinfo.model.Hobbie;
import org.luizinfo.model.Internationalization;
import org.luizinfo.model.Media;
import org.luizinfo.model.Pessoa;
import org.luizinfo.model.Usuario;
import org.luizinfo.repository.IPessoa;
import org.luizinfo.repository.IUsuario;
import org.luizinfo.service.JWTTokenAutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Métodos do Controller de Pessoas")
@RequestMapping(value = "/pessoa")
public class PessoaController implements CrudController<Pessoa> {
	
	@Autowired
	private IPessoa iPessoa;
	
	@Autowired
	private IUsuario iUsuario;
	
	@Autowired
	private JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

	@Override
	@CacheEvict(value="cacheListarPessoas")
	@CachePut(value = "cacheListarPessoas")
	public ResponseEntity<?> listar() {
		
		List<Pessoa> pessoas = iPessoa.findAll();
		
		if (pessoas.size() > 0) {
			return new ResponseEntity<List<Pessoa>>(pessoas, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Não foram encontradas pessoas cadastradas!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<?> inserir(@RequestBody Pessoa objeto, @RequestHeader(name="Authorization") String token) {
		
		if (objeto.getId() != null) {
			return new ResponseEntity<String>("ID da Pessoa não deve ser Informado para cadastrar!", HttpStatus.BAD_REQUEST);
		}
		for (Media media : objeto.getMedias()) {
			media.setPessoa(objeto);
		}
		for (Hobbie hobbie : objeto.getHobbies()) {
			hobbie.setPessoa(objeto);
		}
		for (Internationalization internationalization : objeto.getInternationalizations()) {
			internationalization.setPessoa(objeto);
		}
		
		Pessoa pessoaAux = iPessoa.save(objeto);

		Usuario usuario = iUsuario.findByLogin(jwtTokenAutenticacaoService.getLoginUsuarioToken(token));
		usuario.setPessoa(pessoaAux);
		iUsuario.save(usuario);
		
		return new ResponseEntity<Pessoa>(pessoaAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> atualizar(@RequestBody Pessoa objeto, @RequestHeader(name="Authorization") String token) {
		
		if (objeto.getId() == null) {
			return new ResponseEntity<String>("ID da Pessoa não foi Informado para atualizar!", HttpStatus.BAD_REQUEST);
		}
		
		Pessoa pessoaAux = iPessoa.save(objeto);
		
		return new ResponseEntity<Pessoa>(pessoaAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id, @RequestHeader(name="Authorization") String token) {
		Optional<Pessoa> pessoaOp = iPessoa.findById(id);
		
		if (pessoaOp.isPresent()) {
			iPessoa.delete(pessoaOp.get());
			return new ResponseEntity<String>("Pessoa Excluída com Sucesso!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Pessoa não Encontrada com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> localizar(@PathVariable(value = "id") Long id) {
		Optional<Pessoa> pessoaOp = iPessoa.findById(id);
		
		if (pessoaOp.isPresent()) {
			return new ResponseEntity<Pessoa>(pessoaOp.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Pessoa não Encontrada com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Localizar Pessoas por uma parte do nome")
	@GetMapping(value = "/listar/{nome}", produces = "application/json")
	public ResponseEntity<?> localizar(@PathVariable(value = "nome") String nome) throws InterruptedException {
		
		List<Pessoa> pessoas = iPessoa.findByNomeLike(nome.toUpperCase());
		return new ResponseEntity<List<Pessoa>>(pessoas, HttpStatus.OK);
	}

	@ApiOperation(value = "Retornar Pessoa vinculada a um Usuário")
	@GetMapping(value = "/usuario/{loginUser}", produces = "application/json")
	public ResponseEntity<?> localizarPorUsuario(@PathVariable(value = "loginUser") String loginUser) {
		Usuario usuario = iUsuario.findByLogin(loginUser);
		if (usuario != null) {
			Pessoa pessoa = usuario.getPessoa();
			
			if (pessoa == null) {
				return new ResponseEntity<Pessoa>(new Pessoa(), HttpStatus.OK);
			} else {
				return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("Usuário não encontrado com o Login: " + loginUser, HttpStatus.BAD_REQUEST);
		}
	}
}
