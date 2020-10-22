package ru.dosport.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dosport.services.api.UserSportTypeService;

/**
 * Контроллер списка навыков Пользователя
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/profile/sporttype")
@RequiredArgsConstructor
public class UserSportTypeController {
    // Тип данных
    private final String DATA_TYPE = "application/json";

    // Необходимые сервисы
    private final UserSportTypeService userSportTypeService;

    //todo дописать контроллер
}
