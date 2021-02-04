## Run with docker

`docker-compose up -d`

## Run locally
Change `spring.data.mongodb.uri` in `src/main/resources/application.yml` to: `mongodb://root:secret@localhost:27017/wp-db?authSource=admin`

`gradle bootRun`

## API can be found at:

http://localhost:8080/swagger-ui/index.html

## Running tests
JUnit tests needs to be run with Intellij rather than gradle in `Build tools -> Gradle -> build and run` settings

# Spring-Boot
By making use of Spring Boot I was able to build this api relatively easily.
All Services operate independently (Micro Services) and through Spring's Dependency Injection we do not have to worry about implementation of stuff that would otherwise clutter the application.
By using criteria annotations, data validation is made sensible and can be easily read and understood by others.
After adding error handling we can make the errors display as useful as possible for any user of this API.