package org.luizinfo.repository;

import org.luizinfo.model.Internationalization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInternationalization extends JpaRepository<Internationalization, Long> {

}
