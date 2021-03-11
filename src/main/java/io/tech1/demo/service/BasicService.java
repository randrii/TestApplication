package io.tech1.demo.service;

import io.tech1.demo.entity.Article;
import io.tech1.demo.entity.User;

import java.util.List;
import java.util.Set;

public interface BasicService {

    Article saveArticle(long userId, Article article);

    List<User> findUsersByArticleColor(String colorName);

    User saveUser(User user);

    List<User> findUsersByAge(int age);

    Set<String> findUniqueUserNamesWithThreeArticles();
}
