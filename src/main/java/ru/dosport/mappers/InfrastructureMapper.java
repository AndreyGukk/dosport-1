package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.enums.Infrastructure;

import java.util.Set;

/**
 * Маппер, преобразующий классы Infrastructure и String друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InfrastructureMapper {

    default String mapEnumToString(Infrastructure entity) {
        if (entity != null) {
            return entity.getDescription();
        } else {
            return null;
        }
    }

    default Infrastructure mapStringToEnum(String string) {
        Infrastructure entity;
        switch (string) {
            case "Раздевалка":
                entity = Infrastructure.LOCKER_ROOM;
                break;
            case "Парковка":
                entity = Infrastructure.PARKING;
                break;
            case "Трибуны":
                entity = Infrastructure.STANDS;
                break;
            case "Душ":
                entity = Infrastructure.SHOWER;
                break;
            case "Освещение":
                entity = Infrastructure.LIGHTNING;
                break;
            case "Камера хранения":
                entity = Infrastructure.STORAGE_ROOM;
                break;
            case "Аренда оборудования":
                entity = Infrastructure.EQUIPMENT_RENTAL;
                break;
            case "Сауна":
                entity = Infrastructure.SAUNA;
                break;
            default:
                entity = null;
                break;
        }
        return entity;
    }

    Set<Infrastructure> mapStringToEnum(Set<String> stringSet);

    Set<String> mapEnumToString(Set<Infrastructure> stringSet);
}
