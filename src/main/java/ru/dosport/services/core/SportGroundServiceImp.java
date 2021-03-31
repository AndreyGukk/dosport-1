package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.entities.SportGround;
import ru.dosport.entities.User;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.EventMapper;
import ru.dosport.mappers.InfrastructureMapper;
import ru.dosport.mappers.SportGroundMapper;
import ru.dosport.mappers.UserMapper;
import ru.dosport.repositories.EventRepository;
import ru.dosport.repositories.SportGroundRepository;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;
import ru.dosport.specifications.SportGroundSearchCriteria;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_FOUND_BY_ID;
import static ru.dosport.helpers.Patterns.EVENT_SORT_BY_DATE;
import static ru.dosport.helpers.Patterns.PAGE_SIZE;
import static ru.dosport.specifications.SportGroundSpecifications.sportGroundHasSearchCriteria;

/**
 * Сервис Спортивных площадок.
 */
@Service
@RequiredArgsConstructor
public class SportGroundServiceImp implements SportGroundService {

    // Репозитории
    private final SportGroundRepository groundRepository;
    private final EventRepository eventRepository;

    // Мапперы
    private final SportGroundMapper groundMapper;
    private final UserService userService;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final SportTypeService sportTypeService;
    private final InfrastructureMapper infrastructureMapper;


    @Override
    public List<SportGroundDto> getAllDtoBySearchCriteria(SportGroundSearchCriteria searchCriteria, Integer pageNumber) {
        return groundMapper.mapEntityToDto(groundRepository.findAll(sportGroundHasSearchCriteria(searchCriteria)));
    }

    @Override
    public SportGroundDto getDtoById(Long id) {
        return groundMapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<SportGroundDto> getAllDto() {
        return groundMapper.mapEntityToDto(groundRepository.findAll());
    }


    @Transactional
    @Override
    public SportGroundDto create(SportGroundRequest request) {
        SportGround sportGround = groundMapper.mapDtoToEntity(request);
        sportGround.setSportTypes(sportTypeService.getAllSportTypesByTitle(request.getSportTypes()));
        return groundMapper.mapEntityToDto(groundRepository.save(sportGround));
    }

    @Transactional
    @Override
    public SportGroundDto update(Long id, SportGroundRequest request, Authentication authentication) {
        checkAdminAccess(authentication);

        var sportGround = groundMapper.update(findById(id), request);
        sportGround.setSportTypes(sportTypeService.getAllSportTypesByTitle(request.getSportTypes()));
        return groundMapper.mapEntityToDto(groundRepository.save(sportGround));
    }

    @Transactional
    @Override
    public boolean delete(Long id, Authentication authentication) {
        checkAdminAccess(authentication);

        groundRepository.deleteById(id);
        return groundRepository.existsById(id);
    }

    @Override
    public boolean exists(Long sportGroundId) {
        return groundRepository.existsById(sportGroundId);
    }

    /**
     * Найти по идентификатору
     */
    private SportGround findById(Long id) {
        return groundRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }

    private void checkAdminAccess(Authentication authentication) {
        if (!Roles.hasAuthenticationRoleAdmin(authentication)) {
            throw new AccessDeniedException("Пользователь не является админом");
        }
    }

    @Override
    public Set<SportGroundDto> getFavoriteSportGroundsByAuth(Authentication authentication) {
        Long userId = userService.getIdByAuthentication(authentication);
        return groundMapper.mapEntityToDto(userService.getById(userId).getSportGrounds());
    }

    @Override
    public boolean addFavoriteSportGroundByAuthAndId(Authentication authentication, Long id) {
        User user = userService.getByAuthentication(authentication);
        Optional<SportGround> sportGround = groundRepository.findById(id);
        user.getSportGrounds().add(sportGround.get());
        return userService.save(user).getSportGrounds().contains(sportGround);
    }

    @Override
    public boolean deleteFavoriteSportGroundByAuthAndId(Authentication authentication, Long id) {
        User user = userService.getByAuthentication(authentication);
        Optional<SportGround> sportGround = groundRepository.findById(id);
        user.getSportGrounds().remove(sportGround.get());
        return userService.save(user).getSportGrounds().contains(sportGround);
    }


    @Override
    public Set<UserDto> getSubscribersBySportGroundId(Long sportGroundId) {
        return userMapper.mapSetEntityToDto(groundRepository.findById(sportGroundId).get().getSubscribers());
    }

    @Override
    public List<EventDto> getEventsBySportGroundId(Long id, Integer page) {
        return eventMapper.mapEntityToDto(eventRepository.findAllBySportGround(id, getPageable(page)));
    }

    @Override
    public Set<String> getInfrastructuresBySportGroundId(Long id) {
        return infrastructureMapper.mapEnumToString(groundRepository.findById(id).get().getInfrastructures());
    }


    /**
     * Возвращает объект сортировки страниц поиска мероприятий
     *
     * @return объект сортировки
     */
    private Pageable getPageable(Integer pageNumber) {
        return pageNumber != null ?
                PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.ASC, EVENT_SORT_BY_DATE) :
                PageRequest.of(0, PAGE_SIZE, Sort.Direction.ASC, EVENT_SORT_BY_DATE);
    }
}
