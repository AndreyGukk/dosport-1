package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.entities.SportGround;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.mappers.SportGroundMapper;
import ru.dosport.mappers.SportTypeMapper;
import ru.dosport.repositories.SportGroundRepository;
import ru.dosport.services.api.SportGroundService;

import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

/**
 * Сервис Спортивных площадок.
 */
@Service
@RequiredArgsConstructor
public class SportGroundServiceImp implements SportGroundService {

    // Репозитории
    private final SportGroundRepository groundRepository;

    // Мапперы
    private final SportGroundMapper groundMapper;
    private final SportTypeMapper typeMapper;

    @Override
    public SportGroundDto getDtoById(Long id) {
        return groundMapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<SportGroundDto> getAllDto() {
        return groundMapper.mapEntityToDto(groundRepository.findAll());
    }

    @Override
    public List<SportGroundDto> getAllDto(String city) {
        return city == null ? getAllDto() : groundMapper.mapEntityToDto(groundRepository.findAllByCity(city));
    }

    @Override
    public SportGround getById(Long id) {
        return findById(id);
    }

    @Override
    public SportGroundDto create(SportGroundRequest request) {
        SportGround ground = SportGround.builder()
                .address(request.getAddress())
                .sportType(typeMapper.mapDtoToEntity(request.getSportTypes()))
                .title(request.getTitle())
                .build();

        return groundMapper.mapEntityToDto(groundRepository.save(ground));
    }

    /**
     * Найти по идентификатору
     */
    private SportGround findById(Long id) {
        return groundRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }
}
