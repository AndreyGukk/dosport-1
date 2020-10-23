package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;
import ru.dosport.mappers.SportTypeMapper;
import ru.dosport.repositories.SportTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SportTypeService implements ru.dosport.services.api.SportTypeService {
    private final SportTypeRepository sportTypeRepository;
    private final SportTypeMapper sportTypeMapper;

    @Override
    public List<SportTypeDto> getAllDto() {
        return sportTypeRepository.findAll().stream().map(sportTypeMapper :: mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public SportTypeDto save(SportTypeDto sportTypeDto) {
         sportTypeRepository.save(sportTypeMapper.mapDtoToEntity(sportTypeDto));
        return sportTypeDto;
    }

    @Override
    public Boolean deleteById(Long id) {
        sportTypeRepository.deleteById(id);
        return sportTypeRepository.existsById(id);
    }
}
