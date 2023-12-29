package ru.skypro.homework.repository;

import org.springframework.security.core.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.User;
/**
 * Интерфейс UserRepository представляет репозиторий для управления информацией о пользователе.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);

     User findById(int userId);

    User findUserByUserName(Authentication authentication);
}

