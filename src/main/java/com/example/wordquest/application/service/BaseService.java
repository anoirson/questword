package com.example.wordquest.application.service;

import com.example.wordquest.application.dto.base.BaseCreateDTO;
import com.example.wordquest.application.dto.base.BaseReadDTO;
import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BaseService<R extends BaseReadDTO, C extends BaseCreateDTO, U extends BaseUpdateDTO> {
    R save(C createDto);
    Optional<R> findById(UUID id);
    List<R> findAll();
    void deleteById(UUID id);
    R update(UUID id, U updateDto);
}