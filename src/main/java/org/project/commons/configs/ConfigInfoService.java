package org.project.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.project.entities.ConfigsEntity;
import org.project.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigInfoService {

    private final ConfigsRepository repository;

    public <T> T get(String code, Class<T> clazz){
        ConfigsEntity configs = repository.findById(code).orElse(null);
        if(configs == null || configs.getValue() == null || configs.getValue().isBlank()){
            return null;
        }
        String value = configs.getValue();
        ObjectMapper om = new ObjectMapper();
        T data = null;
        try {
            data = om.readValue(value,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
}