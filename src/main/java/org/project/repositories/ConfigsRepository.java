package org.project.repositories;

import org.project.entities.ConfigsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigsRepository extends JpaRepository<ConfigsEntity,String> {

}
