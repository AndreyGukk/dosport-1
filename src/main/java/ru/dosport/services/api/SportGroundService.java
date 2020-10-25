package ru.dosport.services.api;

import ru.dosport.dto.SportGroundDto;
import ru.dosport.entities.SportGround;

import java.util.List;

public interface SportGroundService {

    SportGroundDto getSportGroundDtoById(Long id);

    List<SportGroundDto> getAllDto();

    SportGround getSportGroundById(Long id);


}
