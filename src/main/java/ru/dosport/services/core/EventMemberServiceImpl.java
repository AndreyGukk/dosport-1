package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.MemberDto;
import ru.dosport.dto.MemberRequest;
import ru.dosport.entities.EventMember;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.MemberStatus;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.EventMemberMapper;
import ru.dosport.mappers.ParticipationStatusMapper;
import ru.dosport.repositories.EventMemberRepository;
import ru.dosport.services.api.EventMemberService;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.UserService;

import java.util.List;
import java.util.Optional;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class EventMemberServiceImpl implements EventMemberService {

    // Необходимые сервисы, мапперы и репозитории
    private final EventMemberMapper memberMapper;
    private final ParticipationStatusMapper statusMapper;
    private final EventMemberRepository memberRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    public List<MemberDto> readAllMembersByEventId(Long eventId) {
        if (eventService.exist(eventId)) {
            return memberMapper.mapEntityToDto(memberRepository.findAllByEventId(eventId));
        }
        throw new DataNotFoundException("Мероприятие не найдено");
    }

    @Override
    public MemberDto readMember(Long eventId, Long userId) {
        if (eventService.exist(eventId)) {
            return memberMapper.mapEntityToDto(findEventMember(eventId, userId));
        }
        throw new DataNotFoundException("Мероприятие не найдено");
    }

    @Transactional
    @Override
    public MemberDto saveOrUpdateMember(MemberRequest request, Long eventId, Authentication authentication) {
        if (MemberStatus.checkCorrectlyStatus(request)) {
            if (eventService.exist(eventId)) {
                var eventMember = memberRepository.
                        findByUserIdAndEventId(userService.getIdByAuthentication(authentication), eventId);
                if (eventMember.isPresent()) {
                    eventMember.get().setStatus(statusMapper.mapStringToEnum(request.getUserStatus()));
                } else {
                    eventMember = Optional.of(EventMember.builder()
                            .userId(userService.getIdByAuthentication(authentication))
                            .eventId(eventId)
                            .status(statusMapper.mapStringToEnum(request.getUserStatus()))
                            .build());
                }
                return memberMapper.mapEntityToDto(memberRepository.save(eventMember.get()));
            }
            throw new DataNotFoundException("Мероприятие не найдено");
        }
        throw new DataBadRequestException("Статус указан не правильно");
    }

    @Override
    public boolean deleteMember(Long userId, Long eventId) {
        if (userService.existsById(userId)) {
            var member = findEventMember(eventId, userId);
            memberRepository.delete(member);
            return !memberRepository.existsById(userId);
        }
        throw new DataNotFoundException("Такого участника не существует");
    }

    @Override
    public List<EventDto> readMemberEvent(Long userId) {
        return eventService.findAllEventDtoById(memberRepository.findAllEventIdByUserId(userId));
    }

    private EventMember findEventMember(Long eventId, Long userId) {
        return memberRepository.findByUserIdAndEventId(userId, eventId).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, userId)));
    }

    @Transactional
    @Override
    public MemberDto addMember(MemberRequest request, Long eventId, Authentication authentication) {
        boolean isPrivate = eventService.isPrivate(eventId, authentication);
        EventMember eventMember;
        if (isPrivate) {
            if (!Roles.hasAuthenticationRoleAdmin(authentication)) {
                throw new AccessDeniedException("Пользователь не является организатором мероприятия");
            } else {
                eventMember = EventMember.builder()
                        .userId(userService.getIdByAuthentication(authentication))
                        .eventId(eventId)
                        .status(statusMapper.mapStringToEnum(request.getUserStatus()))
                        .build();
                return memberMapper.mapEntityToDto(memberRepository.save(eventMember));
            }
        } else {
            eventMember = EventMember.builder()
                    .userId(userService.getIdByAuthentication(authentication))
                    .eventId(eventId)
                    .status(statusMapper.mapStringToEnum(request.getUserStatus()))
                    .build();
            return memberMapper.mapEntityToDto(memberRepository.save(eventMember));
        }
    }
}
