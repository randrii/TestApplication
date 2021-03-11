package io.tech1.demo.service;

import io.tech1.demo.entity.Article;
import io.tech1.demo.entity.Color;
import io.tech1.demo.entity.User;
import io.tech1.demo.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DefaultServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private DefaultService service;

    @ParameterizedTest
    @ValueSource(strings = {"BLACK", "Black", "black"})
    void shouldFindUsersByArticleColor(String colorName) {
        // given
        List<User> userList = constructUsers();
        List<Article> articles = constructArticles(userList);
        List<User> expectedUsers = Arrays.asList(userList.get(0), userList.get(1));
        when(articleRepository.findAll()).thenReturn(articles);

        // when
        List<User> users = service.findUsersByArticleColor(colorName);

        // then
        Assertions.assertEquals(expectedUsers, users);
        verify(articleRepository, times(1)).findAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"PINK", "123", "_black"})
    void shouldThrowExceptionOnInvalidColor(String colorName) {
        // given
        // when
        // then
        Assertions.assertThrows(ResponseStatusException.class, () -> service.findUsersByArticleColor(colorName));
        verify(articleRepository, times(0)).findAll();
    }

    private List<Article> constructArticles(List<User> users) {
        List<Article> articles = Arrays.asList(new Article("text-1", Color.BLACK),
                new Article("text-2", Color.BLACK), new Article("text-1", Color.WHITE));
        articles.get(0).setUser(users.get(0));
        articles.get(1).setUser(users.get(1));

        return articles;
    }

    private List<User> constructUsers() {
        return Arrays.asList(new User("user-1", 22), new User("user-2", 25), new User("user-3", 33));
    }
}