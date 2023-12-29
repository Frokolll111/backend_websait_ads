package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithUserDetails
    void setPasswordTest(){
        NewPassword newPassword = new NewPassword();
        Authentication authentication = Mockito.mock(Authentication.class);
        UserController userController = new UserController(userService);
        Mockito.when(userService.setPassword(newPassword, authentication)).thenReturn(newPassword);
        ResponseEntity<NewPassword> response = userController.setPassword(newPassword, authentication);

        Mockito.verify(userService, Mockito.times(1)).setPassword(newPassword,authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newPassword, response.getBody());
    }

    @Test
    @WithUserDetails
    void getUserTest() {
        Authentication authentication = Mockito.mock(Authentication.class);
        UserDto userDto = new UserDto();
        UserController userController = new UserController(userService);
        Mockito.when(userService.getUserDto(authentication)).thenReturn(userDto);
        ResponseEntity<UserDto> response = userController.getUser(authentication);

        Mockito.verify(userService, Mockito.times(1)).getUserDto(authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    @WithUserDetails
    void updateUserTest(){
        UpdateUser updateUser = new UpdateUser();
        Authentication authentication = Mockito.mock(Authentication.class);
        UserController userController = new UserController(userService);
        Mockito.when(userService.update(updateUser, authentication)).thenReturn(updateUser);
        ResponseEntity<UpdateUser> response = userController.updateUser(updateUser, authentication);

        Mockito.verify(userService, Mockito.times(1)).update(updateUser,authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updateUser, response.getBody());
    }

    @Test
    @WithUserDetails
    void updateUserImageTest(){
        MultipartFile image = new MockMultipartFile("image", "test-image.jpg", "image/jpeg", "test image content".getBytes());
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", "testPassword");
        UserController userController = new UserController(userService);
        Mockito.doNothing().when(userService).updateImage(image, authentication,"testUser");
        ResponseEntity<String> response = userController.updateUserImage(image, authentication);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @WithUserDetails
    void serveFileTest() {
        String filename = "test-image.jpg";
        byte[] testImageData = "test image content".getBytes();
        UserController userController = new UserController(userService);
        when(userService.getUserImage(filename)).thenReturn(testImageData);

        ResponseEntity<byte[]> response = userController.serveFile(filename);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(testImageData, response.getBody());
    }
}
