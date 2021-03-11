## Installation

1. Download or clone the project.
2. Download all dependencies in `pom.xml`.
3. Run application from IDE.

*\*Intellij IDEA is preferred.*

## API Usage

1. Authenticate with the username and password:

```POST http://localhost:8080/authenticate```
need to provide json of username and password in request body.

2. Get users by age greater than some value:

```GET http://localhost:8080/users/age/{{userAge}}```
where `{{userAge}}` represents int value.

3. Get unique users with more than 3 article:

```GET http://localhost:8080/users/unique```

4. Save user

```POST http://localhost:8080/users``` need to provide json of name and age in request body.

5. Get users by article text color

```GET http://localhost:8080/users/articles/color/{{colorName}}``` where `{{colorName}}` represents enum value of
colors.

6. Save article

```POST http://localhost:8080/users/{{userId}}/articles``` need to provide `{{userId}}` as long value in the URL and
json of text and color in request body.

### Note

- **For all request (except authentication) need to set header `Authorization` with `Bearer <token>` value.**

- **Some configuration can be customized in `application.properties`.**

## Postman Collection

See [this file](/api.collection.json).