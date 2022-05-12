package org.luizinfo.repository;

import java.util.List;

import org.luizinfo.model.Pessoa;
import org.luizinfo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPost extends JpaRepository<Post, Long>{

	@Query("select p from Post p where p.pessoa = :pessoa")
	List<Post> findByPessoa(@Param(value = "pessoa") Pessoa pessoa);

}
