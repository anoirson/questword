package com.example.wordquest.application.dto.factory;

public interface BaseDTOFactory<E, D> {
    D createDTO(E entity);
}