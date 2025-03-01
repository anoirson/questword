package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.dto.base.BaseCreateDTO;
import com.example.wordquest.application.dto.base.BaseReadDTO;
import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import com.example.wordquest.application.service.BaseService;
import com.example.wordquest.presentation.controller.BaseController;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



public abstract class BaseControllerImpl<
        R extends BaseReadDTO,
        C extends BaseCreateDTO,
        U extends BaseUpdateDTO
        >
        implements BaseController<R, C, U> {

    protected final BaseService<R, C, U> baseService;

    protected BaseControllerImpl(BaseService<R, C, U> baseService) {
        this.baseService = baseService;
    }

    @Override
    @PostMapping
    public ResponseEntity<R> create(@RequestBody C createDto) {
        R savedDto = baseService.save(createDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<R> getById(@PathVariable UUID id) {
        R dto = baseService.findById(id).orElse(null);
        return ResponseEntity.ok(dto);
    
    }

    @Override
    @GetMapping
    public ResponseEntity<List<R>> getAll() {
        List<R> list = baseService.findAll();
        return ResponseEntity.ok(list);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<R> update(@PathVariable UUID id, @RequestBody U updateDto) {
        R updatedDto = baseService.update(id, updateDto);
        return ResponseEntity.ok(updatedDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        baseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}