package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.entities.Gender;

/**
 * Маппер, преобразующий классы Gender и GenderDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenderMapper {

    default String mapEntityToString(Gender entity) {
        return entity.getDescription();
    }

    default Gender mapStringToEntity(String string) {
        Gender entity;
        switch (string) {
            case "Не выбран":
            case "0":
            case "":
                entity = Gender.NOT_SELECTED;
                break;
            case "Женский":
            case "1":
                entity = Gender.FEMALE;
                break;
            case "Мужской":
            case "2":
                entity = Gender.MALE;
                break;
            default:
                throw new IllegalStateException("Неверное значение поля Пол: " + string);
        }
        return entity;
    }
}
