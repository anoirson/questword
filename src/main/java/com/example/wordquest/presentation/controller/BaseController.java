package com.example.wordquest.presentation.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

public interface BaseController<T, ID> {
    
    @GetMapping("/{id}")
    Optional<T> getById(@PathVariable ID id);

    @GetMapping
    List<T> getAll();

    @PostMapping
    T create(@RequestBody T entity);

    @DeleteMapping("/{id}")
    void delete(@PathVariable ID id);
}
