package org.luizinfo.repository;

import java.util.List;

import org.luizinfo.model.Internationalization;
import org.luizinfo.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IInternationalization extends JpaRepository<Internationalization, Long> {

	@Query("select i from Internationalization i where i.pessoa = :pessoa")
	List<Internationalization> findByPessoa(@Param(value = "pessoa") Pessoa pessoa);
	
}
