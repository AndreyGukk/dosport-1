package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.mappers.SportTypeMapper;
import ru.dosport.repositories.SportTypeRepository;
import ru.dosport.services.api.UserService;
import ru.dosport.services.api.UserSportTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SportTypeServiceImpl implements ru.dosport.services.api.SportTypeService {
    private final SportTypeRepository sportTypeRepository;
    private final SportTypeMapper sportTypeMapper;
    private final UserSportTypeService userSportTypeService;
    private final UserService userService;

    @Override
    public List<SportTypeDto> getAllDto() {
        return sportTypeMapper.mapEntityToDto(sportTypeRepository.findAll());
    }

    @Override
    public List<SportTypeDto> getAllDto(Authentication authentication) {
        List<String> list = new ArrayList();
        userSportTypeService.getAllDtoByUserId(userService.getIdByAuthentication(authentication)).forEach(s -> list.add(s.getSportType()));
        return getAllDto().stream().filter((str) -> !list.contains(str.getTitle())).collect(Collectors.toList());
    }

    @Override
    public SportTypeDto save(SportTypeDto sportTypeDto) {
        sportTypeRepository.save(sportTypeMapper.mapDtoToEntity(sportTypeDto));
        return sportTypeDto;
    }

    @Override
    public Boolean deleteById(Short id) {
        sportTypeRepository.deleteById(id);
        return sportTypeRepository.existsById(id);
    }
}
