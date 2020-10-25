package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.entities.SportGround;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.mappers.SportGroundMapper;
import ru.dosport.repositories.SportGroundRepository;
import ru.dosport.services.api.SportGroundService;

import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class SportGroundServiceImp implements SportGroundService {

    private final SportGroundRepository repository;

    private final SportGroundMapper mapper;

    @Override
    public SportGroundDto getSportGroundDtoById(Long id) {
        return mapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<SportGroundDto> getAllDto() {
        return mapper.mapEntityToDto(repository.findAll());
    }

    @Override
    public SportGround getSportGroundById(Long id) {
        return findById(id);
    }

    private SportGround findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id))
        );
    }
}
