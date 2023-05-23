package org.project.repositories;

import org.project.entities.BoardDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardDataRepository extends JpaRepository<BoardDataEntity,Long>, QuerydslPredicateExecutor<BoardDataEntity> {

}
