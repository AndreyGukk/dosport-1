package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.entities.Gender;

/**
 * Маппер, преобразующий классы Gender и GenderDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenderMapper {

    default String genderToString(Gender entity) {
        return entity.getDescription();
    }

    default Gender stringToGender(String string) {
        Gender entity;
        switch (string) {
            case "NOT_SELECTED" :
            case "Не выбран":
            case "":
                entity = Gender.NOT_SELECTED;
                break;
            case "FEMALE":
            case "Женский":
                entity = Gender.FEMALE;
                break;
            case "MALE":
            case "Мужской":
                entity = Gender.MALE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + string);
        }
        return entity;
    }
}
