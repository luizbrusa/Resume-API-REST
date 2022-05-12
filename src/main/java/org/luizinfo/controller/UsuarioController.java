package org.luizinfo.controller;

import java.util.List;
import java.util.Optional;

import org.luizinfo.dto.UsuarioDTO;
import org.luizinfo.model.Usuario;
import org.luizinfo.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Métodos do Controller de Usuários")
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController implements CrudController<Usuario> {
	
	@Autowired
	private IUsuario iUsuario;

	@Override
	@CacheEvict(value="cacheListarUsuarios")
	@CachePut(value = "cacheListarUsuarios")
	public ResponseEntity<?> listar() {
		
		List<Usuario> usuarios = iUsuario.findAll();
		
		if (usuarios.size() > 0) {
			return new ResponseEntity<List<Usuario>>(usuarios,HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Não foram encontrados usuários cadastrados!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> inserir(@RequestBody Usuario usuario, @RequestHeader(name="Authorization") String token) {
		
		if (usuario.getId() != null) {
			return new ResponseEntity<String>("ID do Usuário não deve ser Informado para cadastrar!", HttpStatus.BAD_REQUEST);
		}
		
		if (usuario.getSenha() != null) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		}

		Usuario usuarioAux = iUsuario.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario, @RequestHeader(name="Authorization") String token) {
		
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("ID do Usuário não foi Informado para atualizar!", HttpStatus.BAD_REQUEST);
		}

		Optional<Usuario> usuarioOp = iUsuario.findById(usuario.getId());

		if ((usuario.getSenha() != null) && (!usuarioOp.get().getSenha().equals(usuario.getSenha()))) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		} else {
			usuario.setSenha(usuarioOp.get().getSenha());
		}
		
		Usuario usuarioAux = iUsuario.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioAux, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id, @RequestHeader(name="Authorization") String token) {
		Optional<Usuario> usuarioOp = iUsuario.findById(id);
		
		if (usuarioOp.isPresent()) {
			iUsuario.delete(usuarioOp.get());
			return new ResponseEntity<String>("Usuário Excluído com Sucesso!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Usuário não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> localizar(@PathVariable(value = "id") Long id) {
		Optional<Usuario> usuarioOp = iUsuario.findById(id);
		
		if (usuarioOp.isPresent()) {
			return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(usuarioOp.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Usuário não Encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Localizar Usuários por uma parte do login")
	@GetMapping(value = "/listar/{login}", produces = "application/json")
	public ResponseEntity<?> localizar(@PathVariable(value = "login") String login) throws InterruptedException {
		
		List<Usuario> usuarios = iUsuario.findByLoginLike(login.toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuarios,HttpStatus.OK);
	}
}
