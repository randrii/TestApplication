package io.tech1.demo.service;

import io.tech1.demo.entity.Article;
import io.tech1.demo.entity.Color;
import io.tech1.demo.entity.User;
import io.tech1.demo.repository.ArticleRepository;
import io.tech1.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultService implements BasicService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findUsersByAge(int age) {
        if (age < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return userRepository.findAll().stream()
                .filter(user -> user.getAge() > age)
                .collect(Collectors.toList());
    }

    public Set<String> findUniqueUserNamesWithThreeArticles() {
        return userRepository.findAll().stream()
                .filter(user -> user.getArticles().size() >= 3)
                .map(User::getName)
                .collect(Collectors.toSet());
    }

    public Article saveArticle(long userId, Article article) {
        userRepository.findById(userId).ifPresent(article::setUser);
        return articleRepository.save(article);
    }

    public List<User> findUsersByArticleColor(String colorName) {
        Color color = Arrays.stream(Color.values())
                .filter(color1 -> color1.name().equals(colorName.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return articleRepository.findAll().stream()
                .filter(article -> article.getColor().equals(color))
                .map(Article::getUser)
                .distinct()
                .collect(Collectors.toList());
    }
}
