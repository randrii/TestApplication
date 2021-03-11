package io.tech1.demo.controller;

import io.tech1.demo.entity.Article;
import io.tech1.demo.entity.User;
import io.tech1.demo.service.BasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final BasicService service;

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/users/{id}/articles")
    public Article saveArticle(@PathVariable long id, @RequestBody Article article) {
        return service.saveArticle(id, article);
    }

    @GetMapping(path = "/users/age/{age}")
    public List<User> getUsersByAge(@PathVariable int age) {
        return service.findUsersByAge(age);
    }

    @GetMapping(path = "/users/unique")
    public Set<String> getUniqueUserNamesWithThreeArticles() {
        return service.findUniqueUserNamesWithThreeArticles();
    }

    @GetMapping("/users/articles/color/{color}")
    public List<User> findUsersByArticleColor(@PathVariable String color) {
        return service.findUsersByArticleColor(color);
    }
}
