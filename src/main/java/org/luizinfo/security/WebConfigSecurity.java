package org.luizinfo.security;

import org.luizinfo.service.IUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private IUserDetailsService iUserDetailsService;

	//Configura as soliictações de acesso por HTTP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Ativando validação para usuários logados somente com TOKEN
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		//Ativando acesso a página inicial do sistema, e as que não exigem login
		.disable().authorizeRequests().antMatchers("/","/index","/tokenAcesso","/pessoa/{id}","/pessoa/usuario/**","/recuperarLogin/**").permitAll()

		//Liberação para acesso direto à API Swagger
		.antMatchers("/swagger-resources/**","/swagger-ui/**","/v2/**").permitAll()

		//Liberação de CORS para autenticação na API através de Navegadores Web
		.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()

		//URL de Logout, redireciona para a página inicial do sistema após deslogar
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		//Mapeia URL de Logout e invalida o usuário logado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		//Filtrar Requisições de Login para Autenticar
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)

		//Filtrar demais requisições para verificar a presença do TOKEN JWT no Header HTTP
		.addFilterBefore(new JWTApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		//Service que irá consultar o usuário no Banco
		auth.userDetailsService(iUserDetailsService)
		
		//Codificador de Senha
		.passwordEncoder(new BCryptPasswordEncoder());
	
	}
}
