package ru.skypro.homework.exception;
/**
 * Класс NoRightsException реализующий класс RuntimeException
 * который в свою очередь выдаёт ошибку если пользователь не зарегистрирован
 */
public class NoRightsException extends RuntimeException{
    public NoRightsException(String message) {
        super(message);
    }
}
