package com.study.mf.repository;

import com.study.mf.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("""
        SELECT g
        FROM Game g
        WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))
        """)
    Page<Game> findByPartName(@Param("name") String name, Pageable pageable);
}
