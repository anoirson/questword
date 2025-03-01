package com.example.wordquest.application.factory.impl;

import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.dto.player.PlayerUpdateDTO;
import com.example.wordquest.application.factory.PlayerDTOFactory;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.valueObjects.EmailVO;
import org.springframework.stereotype.Component;



@Component
public class PlayerDTOFactoryImpl implements PlayerDTOFactory {

    @Override
    public PlayerReadDTO createReadDto(Player entity) {
        
       return new PlayerReadDTO(entity.getId(), entity.getUsername(), entity.getEmail().getValue());
    }
    @Override
    public Player createEntity(PlayerCreateDTO createDto) {
        EmailVO email = new EmailVO(createDto.getEmail());
        return new Player(createDto.getUsername(), email);
    }
    @Override
    public Player updateEntity(Player entity, PlayerUpdateDTO updateDto) {
        if (updateDto.getUsername() != null) {
            entity.setUsername(updateDto.getUsername());
        }
        if (updateDto.getEmail() != null) {
            EmailVO email = new EmailVO(updateDto.getEmail());
            entity.setEmail(email);
        }
        return entity;
    }  
}
