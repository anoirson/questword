package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.service.BaseService;
import com.example.wordquest.presentation.controller.BaseController;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

public abstract class BaseControllerImpl<T, ID> implements BaseController<T, ID> {

    protected final BaseService<T, ID> service;

    protected BaseControllerImpl(BaseService<T, ID> service) {
        this.service = service;
    }

    @Override
    public Optional<T> getById(@PathVariable ID id) {
        return service.findById(id);
    }

    @Override
    public List<T> getAll() {
        return service.findAll();
    }

    @Override
    public T create(@RequestBody T entity) {
        return service.save(entity);
    }

    @Override
    public void delete(@PathVariable ID id) {
        service.deleteById(id);
    }
}