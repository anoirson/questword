package com.example.wordquest.application.factory;

import com.example.wordquest.application.dto.base.BaseCreateDTO;
import com.example.wordquest.application.dto.base.BaseReadDTO;
import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import com.example.wordquest.domain.model.BaseEntity;

public interface BaseDTOFactory<E extends BaseEntity, R extends BaseReadDTO, C extends BaseCreateDTO, U extends BaseUpdateDTO> {
    R createReadDto(E entity);

    E createEntity(C createDto);

    E updateEntity(E entity, U updateDto);

}
