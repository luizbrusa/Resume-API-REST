package org.luizinfo.repository;

import java.util.List;

import org.luizinfo.model.Pessoa;
import org.luizinfo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPessoa extends JpaRepository<Pessoa, Long> {

	@Query("select p from Pessoa p where upper(p.name) like %:name%")
	List<Pessoa> findByNomeLike(@Param(value = "name") String name);

	@Query("select p from Pessoa p where p = (select u.pessoa from Usuario u where u = :usuario)")
	Pessoa findByUsuario(@Param(value = "usuario") Usuario usuario);
	
}
