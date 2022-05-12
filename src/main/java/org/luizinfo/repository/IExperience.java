package org.luizinfo.repository;

import java.util.List;

import org.luizinfo.model.Experience;
import org.luizinfo.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IExperience extends JpaRepository<Experience, Long> {

	@Query("select e from Experience e where e.pessoa = :pessoa")
	List<Experience> findByPessoa(@Param(value = "pessoa") Pessoa pessoa);

}
