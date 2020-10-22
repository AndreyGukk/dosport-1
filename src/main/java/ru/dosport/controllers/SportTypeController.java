package ru.dosport.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер списка навыков Пользователя
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/sporttype")
@RequiredArgsConstructor
public class SportTypeController {
    //todo для добавления новых видов спорта
}
