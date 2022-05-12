package org.luizinfo.repository;

import org.luizinfo.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedia extends JpaRepository<Media, Long> {

}
