package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.entities.SportType;
import ru.dosport.mappers.SportTypeMapper;
import ru.dosport.repositories.SportTypeRepository;
import ru.dosport.services.api.UserSportTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SportTypeServiceImpl implements ru.dosport.services.api.SportTypeService {
    private final SportTypeRepository sportTypeRepository;
    private final SportTypeMapper sportTypeMapper;
    private final UserSportTypeService userSportTypeService;

    @Override
    public List<SportTypeDto> getAllDto() {
        return sportTypeMapper.mapEntityToDto(sportTypeRepository.findAll());
    }

    @Override
    public List<SportTypeDto> getAllDto(Authentication authentication) {
        List<SportTypeDto> listAll = getAllDto();
        List<UserSportTypeDto> list = userSportTypeService.getAllDtoByUserId(authentication);
        for (SportTypeDto s : listAll) {
            if (list.contains(s.getTitle())) listAll.remove(s);
        }
        return listAll;
        //todo переписать это пока никто не увидел
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
