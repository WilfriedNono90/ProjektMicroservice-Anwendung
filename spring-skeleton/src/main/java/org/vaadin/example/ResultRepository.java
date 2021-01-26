package org.vaadin.example;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultRepository  extends JpaRepository<Result, Long> {
	@Query("select r from Result r where r.idplayer = :id ")
	List<Result> findByIdPlayer(@Param("id") long parseLong);

}
