package org.luizinfo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import javax.mail.MessagingException;

import org.luizinfo.exception.Erro;
import org.luizinfo.model.Usuario;
import org.luizinfo.repository.IUsuario;
import org.luizinfo.service.EmailService;
import org.luizinfo.service.JWTTokenAutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Métodos Públicos da API")
@RestController
public class PublicController {
	
	@Autowired
	private IUsuario iUsuario;

	@ApiOperation(value = "Index de Testes da Aplicação")
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<String> index() {

		//Adicionar as Credenciais do Administrador Padrão do sistema
		Optional<Usuario> opUsuario = iUsuario.findById(1L);
		if (!opUsuario.isPresent()) {
			Usuario usuario = new Usuario();
			usuario.setId(1L);
			usuario.setLogin("admin");
			usuario.setSenha(new BCryptPasswordEncoder().encode("1234"));
			iUsuario.save(usuario);
		}
		
		return new ResponseEntity<String>("Index Resume - Teste da Aplicação \n\nAcesse a API acrescentando \"swagger-ui/index.html\" à URL", HttpStatus.OK);
	}

	@PostMapping(value = "/tokenAcesso", produces = "application/json")
	public ResponseEntity<?> tokenAcesso(@RequestBody Usuario usuario) {

		//Localiza o Usuário pelo Login
		Usuario usuarioAux = iUsuario.findByLogin(usuario.getLogin());

		//Valida o Usuário e Senha passado
		if (usuarioAux != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(usuario.getSenha(), usuarioAux.getSenha())) {
				String token = new JWTTokenAutenticacaoService().montagemTokenJwt(usuario.getLogin());

				return new ResponseEntity<String>("{\"Token\" : \"" + token + "\"}", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Senha Incorreta!", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("Usuário não Encontrado!", HttpStatus.BAD_REQUEST);
		}
	}

	@ResponseBody
	@PostMapping(value = "/recuperarLogin")
	public ResponseEntity<?> recuperarLogin(@RequestBody Usuario usuario) {
		
		Erro erro = new Erro();
		Usuario usuarioAux = iUsuario.findByLogin(usuario.getLogin());
		
		if (usuarioAux == null) {
			erro.setCode("404");
			erro.setError("Usuário não encontrado com o Login: " + usuario.getLogin());
		} else {
			EmailService emailService = new EmailService();
			try {
				SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
				String novaSenha = format.format(Calendar.getInstance().getTime());
				
				usuarioAux.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
				iUsuario.save(usuarioAux);
				
				emailService.enviarEmail("Recuperação de Senha", usuarioAux.getLogin(), "Sua nova senha é: " + novaSenha);

				erro.setCode("200");
				erro.setError("Senha enviada para o seu e-mail");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return new ResponseEntity<Erro>(erro,HttpStatus.OK);
	}
	
}
