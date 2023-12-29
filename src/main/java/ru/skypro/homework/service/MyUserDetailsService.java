package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

import java.nio.file.AccessDeniedException;

/**
 * Класс MyUserDetailsService - это класс представляющий UserDetailsManager
 */
@Component
public class MyUserDetailsService implements UserDetailsManager {

    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override
    public void createUser(UserDetails user) {
//нужна реализация
        User u = new User();
        u.setUserName(user.getUsername());
        u.setPassword(user.getPassword());
        u.setRole(Role.valueOf(user.getAuthorities().iterator().next().getAuthority().substring(5)));
        userRepository.save(u);
    }

    public void createUser(Register register) {
//нужна реализация
        User u = userRepository.findByUserName(register.getUsername());
        u.setPhone(register.getPhone());
        u.setFirstName(register.getFirstName());
        u.setLastName(register.getLastName());
        u.setEmail(register.getUsername());
        userRepository.save(u);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
//нужна реализация
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            try {
                throw new AccessDeniedException(
                        "Can't change password as no Authentication object found in context for current user.");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }

        String username = currentUser.getName();


        // If an authentication manager has been set, re-authenticate the user
        // with the supplied password.
        if (authenticationManager != null) {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        }

        User changedUser = userRepository.findByUserName(username);

        if (changedUser == null) {
            throw new IllegalStateException("Current user doesn't exist in database.");
        }

        changedUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));

        userRepository.save(changedUser);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUserName(username) != null;
    }
}
