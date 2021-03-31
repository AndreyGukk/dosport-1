package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.enums.MetroStation;

import java.util.Set;

/**
 * Маппер, преобразующий классы MetroStation и String друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MetroStationMapper {

    default String mapEnumToString(MetroStation entity) {
        if (entity != null) {
            return entity.getDescription();
        } else {
            return null;
        }
    }

    default MetroStation mapStringToEnum(String string) {
        MetroStation entity;
        switch (string) {
            case "Сокольники":
                entity = MetroStation.SOKOLNIKI;
                break;
            default:
                entity = null;
                break;
        }
        return entity;
    }

    Set<MetroStation> mapStringToEnum(Set<String> stringSet);

    Set<String> mapEnumToString(Set<MetroStation> stringSet);
}
