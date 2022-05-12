package org.luizinfo.repository;

import org.luizinfo.model.Hobbie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHobbie extends JpaRepository<Hobbie, Long> {

}
