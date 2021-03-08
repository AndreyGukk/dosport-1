package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.enums.ParticipationStatus;

/**
 * Маппер, преобразующий классы ParticipationStatus и String друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticipationStatusMapper {

    default String mapEnumToString(ParticipationStatus entity) {
        return entity.getDescription();
    }

    default ParticipationStatus mapStringToEnum(String string) {
        ParticipationStatus entity;
        switch (string) {
            case "Возможно пойду":
                entity = ParticipationStatus.MAYBE;
                break;
            case "Точно пойду":
                entity = ParticipationStatus.DEFINITELY;
                break;
            default:
                entity = ParticipationStatus.NOT_SELECTED;
        }
        return entity;
    }
}
