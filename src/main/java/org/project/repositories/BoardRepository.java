package org.project.repositories;

import org.project.entities.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<BoardEntity,String>, QuerydslPredicateExecutor<BoardEntity> {

}
