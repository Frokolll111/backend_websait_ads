package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;


public class NoRightsException extends RuntimeException{
    public NoRightsException(String message) {
        super(message);
    }
}
