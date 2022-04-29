package org.luizinfo.service;

import org.luizinfo.model.Usuario;
import org.luizinfo.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuario iUsuario;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = iUsuario.findByLogin(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário " + username + " não Encontrado.");
		} else {
			return new User(usuario.getLogin(),usuario.getPassword(),usuario.getAuthorities());
		}
	}

}
