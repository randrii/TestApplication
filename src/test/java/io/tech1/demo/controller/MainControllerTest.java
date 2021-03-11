package io.tech1.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.TestConfig;
import io.tech1.demo.entity.User;
import io.tech1.demo.service.BasicService;
import io.tech1.demo.util.JwtUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
@Import(TestConfig.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BasicService service;
    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Test
    void shouldReturnForbiddenOnUniqueUserNamesWithThreeArticles() {
        // given
        when(service.findUniqueUserNamesWithThreeArticles()).thenReturn(new HashSet<>());

        // when
        // then
        mockMvc.perform(get("/users/unique"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(""));

        verify(service, times(0)).findUniqueUserNamesWithThreeArticles();
    }

    @SneakyThrows
    @Test
    void shouldReturnOkOnUniqueUserNamesWithThreeArticles() {
        //given
        Set<String> usernameSet = new HashSet<>(Arrays.asList("user-1", "user-2"));
        when(service.findUniqueUserNamesWithThreeArticles()).thenReturn(usernameSet);
        String responseString = mapper.writeValueAsString(usernameSet);
        String token = new JwtUtil().createToken(new HashMap<>(), "user");

        // when
        // then
        mockMvc.perform(get("/users/unique")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(responseString));

        verify(service, times(1)).findUniqueUserNamesWithThreeArticles();
    }

    @SneakyThrows
    @Test
    void shouldReturnForbiddenOnFindUsersByArticleColor() {
        // given
        String validColorName = "BLACK";
        when(service.findUsersByArticleColor(validColorName)).thenReturn(new ArrayList<>());
        // when
        // then
        mockMvc.perform(get("/users/articles/color/{color}", validColorName))
                .andExpect(status().isForbidden());

        verify(service, times(0)).findUniqueUserNamesWithThreeArticles();
    }

    @SneakyThrows
    @Test
    void shouldReturnNotFoundOnFindUsersByArticleColor() {
        // given
        String invalidColor = "InvalidColor";
        when(service.findUsersByArticleColor(invalidColor)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        String token = new JwtUtil().createToken(new HashMap<>(), "user");

        // when
        // then
        mockMvc.perform(get("/users/articles/color/{color}", invalidColor)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findUsersByArticleColor(eq(invalidColor));
    }

    @SneakyThrows
    @Test
    void shouldReturnOkOnFindUsersByArticleColor() {
        // given
        String validColorName = "BLACK";
        List<User> userList = Arrays.asList(new User("user-1", 12), new User("user-2", 22));
        when(service.findUsersByArticleColor(validColorName)).thenReturn(userList);
        String responseString = mapper.writeValueAsString(userList);
        String token = new JwtUtil().createToken(new HashMap<>(), "user");

        // when
        // then
        mockMvc.perform(get("/users/articles/color/{color}", validColorName)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(responseString));

        verify(service, times(1)).findUsersByArticleColor(eq(validColorName));
    }
}