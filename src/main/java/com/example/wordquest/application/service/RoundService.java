package com.example.wordquest.application.service;

import java.util.List;
import java.util.UUID;

import com.example.wordquest.application.dto.round.RoundCreateDTO;
import com.example.wordquest.application.dto.round.RoundReadDTO;
import com.example.wordquest.application.dto.round.RoundUpdateDTO;

public interface RoundService extends BaseService<RoundReadDTO, RoundCreateDTO, RoundUpdateDTO> {
    List<RoundReadDTO> findAllByGameId(UUID gameId);
}
