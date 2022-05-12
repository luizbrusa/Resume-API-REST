package org.luizinfo.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.luizinfo.component.ApplicationContextLoad;
import org.luizinfo.model.Usuario;
import org.luizinfo.repository.IUsuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

	//Tempo de Validade do TOKEN em milissegundos = 2 dias
	private static final long EXPIRATION_TIME = 172800000;
//	private static final long EXPIRATION_TIME = 60000; //1 minuto, somente para testes

	//Uma senha única para compor a autenticação e ajudar na segurança
	private static final String SECRET_KEY = "SenhaForte";
	
	//Prefixo padrão de TOKEN
	private static final String TOKEN_PREFIX = "Bearer";
	
	//Cabeçalho padrão de Autorização do TOKEN
	private static final String HEADER_STRING = "Authorization";

	//Montar o Token JWT de Autenticação
	public String getTokenJwt(String userName) {
		//Montagem do TOKEN
		String jwt = Jwts.builder() //Geração do TOKEN
				.setSubject(userName) //Adiciona o Usuário já validado ao Token
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //Tempo de Expiração
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact(); //Compactação e geração de Senha

		//Atualiza o TOKEN para o Usuário logando
		ApplicationContextLoad.getApplicationContext().getBean(IUsuario.class).atualizaTokenUsuario(userName, jwt);

		//Adiciona o Prefixo ao Token e retorna
		return jwt;
	}

	//Recupera o Usuário a partir do TOKEN JWT da requisição
	public String getLoginUsuarioToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody().getSubject();
	}

	//Gerando TOKEN de autenticação e adicionando ao cabeçalho a resposta HTTP
	public void addAuthentication(HttpServletRequest request, HttpServletResponse response, String userName) throws IOException {
		//Monta o Token com o Usuário já validado anteriormente
		String token = getTokenJwt(userName);
		//Adiciona o Token no Cabeçalho HTTP da Resposta
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
		//Libera Cors para qualquer aplicação externa acessar a API
		liberacaoCors(response);
		//Adiciona o Token como resposta no corpo do HTTP
		response.getWriter().write("{\"" + HEADER_STRING + "\" : \"" + token + "\"}");
	}
	
	//Retorna o Usuário Validado com o TOKEN, se não for válido, retorna NULL
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		//recupera o TOKEN do cabeçalho HTTP
		String header = request.getHeader(HEADER_STRING);
		liberacaoCors(response);

		if (header != null) {
			//Recupera o Token da requisição para comparar com o do Usuário
			String token = header.replace(TOKEN_PREFIX, "").trim();
			
			try {
				if (token != null) {
					String user = getLoginUsuarioToken(token);
					if (user != null) {
						Usuario usuario = ApplicationContextLoad.getApplicationContext()
								.getBean(IUsuario.class).findByLogin(user);
						
						//Retorna o usuário logado
						if (usuario != null) {
							//Valida o Token do Usuário com o Token da Requisição
							if (token.equals(usuario.getToken())) {
								return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
							}
						}
					}
				}
			} catch (ExpiredJwtException e) {
				try {
					response.getOutputStream().println("TOKEN Expirado, Faça novo login, ou informe um novo TOKEN para autenticar!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		return null; //Não Autorizado
	}

	private void liberacaoCors(HttpServletResponse response) {

		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}

		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}

		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}

		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*" );
		}
	}
	
}
