package ru.skypro.homework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.UnavailableException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@ExtendWith(SpringExtension.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    @WithUserDetails
    void getCommentTest() throws Exception {
        int adId = 1;
        List<CommentDto> commentDtoList = new ArrayList<>();
        Comments expectedComments = new Comments(commentDtoList);

        when(commentService.getCommentsByAdId(adId)).thenReturn(expectedComments);

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/{id}/comments", adId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(commentService, times(1)).getCommentsByAdId(adId);
    }

    @Test
    @WithUserDetails
    void addCommentTest() throws Exception {
        int adId = 1;
        CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment();
        createOrUpdateComment.setText("Test Comment");

        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", "password");


        CommentDto commentDto = new CommentDto();
        commentDto.setPk(1);
        commentDto.setText("Test Comment");

        when(commentService.addComment(adId, createOrUpdateComment, authentication))
                .thenReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/{id}/comments", adId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @WithUserDetails
    void deleteCommentTest() throws Exception {
        int adId = 1;
        int commentId = 1;

        Authentication authentication = Mockito.mock(Authentication.class);
        CommentService commentService = Mockito.mock(CommentService.class);

        Mockito.doNothing().when(commentService).deleteComment(adId, commentId, authentication);
        CommentController commentController = new CommentController(commentService);
        ResponseEntity<Void> response = commentController.deleteComment(adId, commentId, authentication);

        Mockito.verify(commentService, Mockito.times(1)).deleteComment(adId, commentId,authentication);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @WithUserDetails
    void updateCommentTest() throws Exception {
        int adId = 1;
        int commentId = 1;
        CreateOrUpdateComment updateComment = new CreateOrUpdateComment();
        Authentication authentication = Mockito.mock(Authentication.class);
        CommentController commentController = new CommentController(commentService);
        Mockito.when(commentService.updateComment(adId, commentId, updateComment ,authentication)).thenReturn(updateComment);
        ResponseEntity<CreateOrUpdateComment> response = commentController.updateComment(adId, commentId, updateComment ,authentication);

        Mockito.verify(commentService, Mockito.times(1)).updateComment(adId, commentId, updateComment ,authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updateComment, response.getBody());
    }

}

