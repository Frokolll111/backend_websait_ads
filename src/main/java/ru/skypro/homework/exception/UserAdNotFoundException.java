package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;
/**
 * Класс UserAdNotFoundException реализующий класс RuntimeException
 * который в свою очередь выдаёт ошибку если у пользователя нету объявлений
 */
@RequiredArgsConstructor
public class UserAdNotFoundException extends RuntimeException {

    private final int id;
    public String getMessage() {
        return "У пользователя с таким id: " + id + " - объявлений нет";
    }
}
