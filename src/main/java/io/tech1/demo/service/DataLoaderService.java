package io.tech1.demo.service;

import io.tech1.demo.entity.Article;
import io.tech1.demo.entity.Color;
import io.tech1.demo.entity.User;
import io.tech1.demo.repository.ArticleRepository;
import io.tech1.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataLoaderService implements ApplicationRunner {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private Random random = new Random();
    @Value("${user.count}")
    private int userCount;
    @Value("${article.count}")
    private int articleCount;


    @Override
    public void run(ApplicationArguments args) {
        List<User> users = constructUsers();
        userRepository.saveAll(users);

        List<Article> articles = constructArticles();
        articles.forEach(article -> bindUser(article, users));
        articleRepository.saveAll(articles);
    }

    private void bindUser(Article article, List<User> users) {
        article.setUser(users.get(random.nextInt(users.size())));
    }

    private List<User> constructUsers() {
        return IntStream.range(0, userCount)
                .mapToObj(i -> new User("user-" + (i + 1), random.nextInt(userCount)))
                .collect(Collectors.toList());
    }

    private List<Article> constructArticles() {
        return IntStream.range(0, articleCount)
                .mapToObj(i -> new Article("text-" + (i + 1), chooseRandomColor()))
                .collect(Collectors.toList());
    }

    private Color chooseRandomColor() {
        return Color.values()[random.nextInt(Color.values().length)];
    }
}
