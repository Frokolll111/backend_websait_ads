package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdNotFoundException  extends  RuntimeException{
    private final int pk;
    @Override
    public String getMessage(){
        return "Объявление с таким id: " + pk + " - не найдено";
    }
}
