package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;
/**
 * Класс UserAdNotFoundException реализующий класс RuntimeException
 * который в свою очередь выдаёт ошибку если Объявление с таким id не найдено
 */
@RequiredArgsConstructor
public class AdNotFoundException  extends  RuntimeException{
    private final int pk;
    @Override
    public String getMessage(){
        return "Объявление с таким id: " + pk + " - не найдено";
    }
}
