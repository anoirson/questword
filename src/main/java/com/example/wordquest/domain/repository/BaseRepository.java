package com.example.wordquest.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.example.wordquest.domain.model.Game;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
}