package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.mappers.SportTypeMapper;
import ru.dosport.repositories.SportTypeRepository;
import ru.dosport.services.api.SportTypeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

/**
 * Реализация сервиса Видов спорта.
 */
@Service
@RequiredArgsConstructor
public class SportTypeServiceImpl implements SportTypeService {

    // Необходимые мапперы и репозитории
    private final SportTypeMapper mapper;
    private final SportTypeRepository repository;

    @Override
    public SportTypeDto getSportTypeDtoById(Short id) {
        return mapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<SportTypeDto> getAllSportTypeDto() {
        return mapper.mapEntityToDto(repository.findAll());
    }

    @Override
    public SportType getSportTypeByTitle(String title) {
        return repository.findByTitle(title).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, title)));
    }

    @Transactional
    @Override
    public SportTypeDto save(String sportTitle) {
        Optional<SportType> sport = repository.findByTitle(sportTitle);
        return sport.isPresent() ?
                mapper.mapEntityToDto(sport.get()) : mapper.mapEntityToDto(repository.save(new SportType(sportTitle)));
    }

    @Override
    public Boolean deleteById(Short id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public SportTypeDto update(Short id, String tittle) {
        SportType sportType = findById(id);
        sportType.setTitle(tittle);
        return mapper.mapEntityToDto(repository.save(sportType));
    }

    /**
     * Найти по идентификатору
     */
    private SportType findById(Short id) {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }
}
