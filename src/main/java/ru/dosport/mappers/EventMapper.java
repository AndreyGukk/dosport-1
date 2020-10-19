package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.entities.Event;

import java.util.List;

/**
 * Маппер, преобразующий классы Event и EventDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface EventMapper {

    // TODO:
    // 1. Дописать сущности Event и Field
    // 2. Создать FieldMapper и SportTypeMapper,
    // 3. Добавить их использование: uses = {FieldMapper.class, SportTypeMapper.class}
    // 4. Убрать игнорирование полей sportType и field в настройках маппинга

    /*
    @Mappings({
            @Mapping(target="sportType", ignore = true),
            @Mapping(target="field", ignore = true),
            @Mapping(target="startDateTime", source = "entity.startDateTime", dateFormat = "dd-MM-yyyy hh:mm"),
            @Mapping(target="stopDateTime", source = "entity.stopDateTime", dateFormat = "dd-MM-yyyy hh:mm")
    })
    EventDto mapEntityToDto(Event entity);

    List<EventDto> mapEntityToDto(List<Event> entities);

    @Mappings({
            @Mapping(target="sportType", ignore = true),
            @Mapping(target="field", ignore = true),
            @Mapping(target="startDateTime", source="dto.startDateTime", dateFormat = "dd-MM-yyyy hh:mm"),
            @Mapping(target="stopDateTime", source="dto.stopDateTime", dateFormat = "dd-MM-yyyy hh:mm")
    })
    Event mapDtoToEntity(EventDto dto);

    Event mapDtoToEntity(EventRequest dto);

    @Mappings({
            @Mapping(target="sportType", ignore = true),
            @Mapping(target="field", ignore = true),
            @Mapping(target="id", ignore = true),
            @Mapping(target="startDateTime", source="dto.startDateTime", dateFormat = "dd-MM-yyyy hh:mm"),
            @Mapping(target="stopDateTime", source="dto.stopDateTime", dateFormat = "dd-MM-yyyy hh:mm")
    })
    Event update(@MappingTarget Event entity, EventDto dto);
    */
}
