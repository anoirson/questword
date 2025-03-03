package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.round.*;
import com.example.wordquest.application.factory.impl.RoundDTOFactoryImpl;
import com.example.wordquest.application.service.RoundService;
import com.example.wordquest.domain.model.Round;
import com.example.wordquest.domain.repository.RoundRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoundServiceImpl
        extends BaseServiceImpl<Round, RoundReadDTO, RoundCreateDTO, RoundUpdateDTO>
        implements RoundService {

    private final RoundRepository roundRepository;
    private final RoundDTOFactoryImpl roundDTOFactory;

    public RoundServiceImpl(RoundRepository roundRepository, RoundDTOFactoryImpl roundDTOFactory) {
        super(roundRepository, roundDTOFactory);
        this.roundRepository = roundRepository;
        this.roundDTOFactory = roundDTOFactory;
    }

    @Override
    public List<RoundReadDTO> findAllByGameId(UUID gameId) {
        List<Round> rounds = roundRepository.findAllByGameId(gameId);
        return rounds.stream()
                .map(roundDTOFactory::createReadDto)
                .toList();
    }

}
