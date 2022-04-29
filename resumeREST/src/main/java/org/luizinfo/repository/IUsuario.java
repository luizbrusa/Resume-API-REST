package org.luizinfo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.luizinfo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Long>{
	
	@Query("select u from Usuario u where u.login = :login")
	Usuario findByLogin(@Param(value = "login") String login);

	@Query("select u from Usuario u where upper(u.login) like %:login%")
	List<Usuario> findByLoginLike(@Param(value = "login") String login);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update usuario set token = :token where login = :login")
	void atualizaTokenUsuario(@Param(value = "login") String login, @Param(value = "token") String token);

}
