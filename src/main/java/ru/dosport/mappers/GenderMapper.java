package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.enums.Gender;

/**
 * Маппер, преобразующий классы Gender и GenderDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenderMapper {

    default String mapEnumToString(Gender entity) {
        if (entity != null) {
            return entity.getDescription();
        } else {
            return null;
        }
    }

    default Gender mapStringToEnum(String string) {
        Gender entity;
        switch (string) {
            case "Женский":
                entity = Gender.FEMALE;
                break;
            case "Мужской":
                entity = Gender.MALE;
                break;
            default:
                entity = null;
                break;
        }
        return entity;
    }
}
