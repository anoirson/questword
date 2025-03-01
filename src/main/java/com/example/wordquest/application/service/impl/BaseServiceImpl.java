package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.base.BaseCreateDTO;
import com.example.wordquest.application.dto.base.BaseReadDTO;
import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import com.example.wordquest.application.factory.BaseDTOFactory;
import com.example.wordquest.application.service.BaseService;
import com.example.wordquest.domain.model.BaseEntity;
import com.example.wordquest.domain.repository.BaseRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseServiceImpl<
        E extends BaseEntity, 
        R extends BaseReadDTO, 
        C extends BaseCreateDTO, 
        U extends BaseUpdateDTO
    >
    implements BaseService<R, C, U> {

    protected final BaseRepository<E, UUID> repository;
    protected final BaseDTOFactory<E, R, C, U> factory;

    protected BaseServiceImpl(BaseRepository<E, UUID> repository,
                              BaseDTOFactory<E, R, C, U> factory) {
        this.repository = repository;
        this.factory = factory;
    }
    @Override
    @Transactional
    public R save(C createDto) {
        E entity = factory.createEntity(createDto);
        E savedEntity = repository.save(entity);
        return factory.createReadDto(savedEntity);
    }
    @Override
    public Optional<R> findById(UUID id) {
        E entity = repository.findById(id).orElse(null);
        return Optional.ofNullable(entity).map(factory::createReadDto);
    }
    @Override
    @Transactional
    public void deleteById(UUID id) {
        E entity = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Entity not found"));
        repository.delete(entity);
    }   

    @Override
    public R update(UUID id, U updateDto) {
        E entity = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Entity not found"));
        E updatedEntity = factory.updateEntity(entity, updateDto);
        E savedEntity = repository.save(updatedEntity);
        return factory.createReadDto(savedEntity);
    }

    @Override
    public List<R> findAll() {
        return repository.findAll()
                         .stream()
                         .map(factory::createReadDto)
                         .toList();
    }
    
}