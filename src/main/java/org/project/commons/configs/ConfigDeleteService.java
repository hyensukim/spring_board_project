package org.project.commons.configs;

import lombok.RequiredArgsConstructor;
import org.project.entities.ConfigsEntity;
import org.project.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigsRepository repository;

    public void delete(String code){
        ConfigsEntity configs = repository.findById(code).orElse(null);
        if(configs == null){
            return;
        }
        repository.delete(configs);
        repository.flush();
    }
}
