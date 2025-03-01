package com.example.wordquest.presentation.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;



public interface BaseController<R, C, U> {
    
    ResponseEntity<R> create(C createDto);

    ResponseEntity<R> getById(UUID id);

    ResponseEntity<List<R>> getAll();

    ResponseEntity<R> update(UUID id, U updateDto);

    ResponseEntity<Void> delete(UUID id);
}
