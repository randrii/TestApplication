package io.tech1.demo.repository;

import io.tech1.demo.entity.Article;
import io.tech1.demo.entity.Color;
import io.tech1.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        articleRepository.deleteAll();
    }

    @Test
    void shouldSaveArticles() {
        // given
        User user = new User("user", 12);
        userRepository.save(user);

        List<Article> articles = constructArticles();
        articles.get(0).setUser(user);

        // when
        List<Article> articleList = articleRepository.saveAll(articles);

        // then
        assertEquals(articleList, articles);
        assertEquals(user, userRepository.getOne(1L));
        assertEquals(articles.size(), articleRepository.count());
    }

    private List<Article> constructArticles() {
        return Arrays.asList(new Article("text-1", Color.BLACK), new Article("text-2", Color.BLUE),
                new Article("text-3", Color.BLACK));
    }
}