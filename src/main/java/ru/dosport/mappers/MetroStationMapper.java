package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.enums.MetroStation;

/**
 * Маппер, преобразующий классы MetroStation и String друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MetroStationMapper {

    default String mapEnumToString(MetroStation entity) {
        return entity.getDescription();
    }

    default MetroStation mapStringToEnum(String string) {
        MetroStation entity;
        switch (string) {
            case "Сокольники":
                entity = MetroStation.SOKOLNIKI;
                break;
            default:
                entity = MetroStation.NOT_SELECTED;
                break;
        }
        return entity;
    }
}
