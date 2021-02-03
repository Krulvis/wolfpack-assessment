## Run with docker

`docker-compose up -d`

## Run locally
Change `spring.data.mongodb.uri` in `src/main/resources/application.yml` to: `mongodb://root:secret@localhost:27017/wp-db?authSource=admin`

`gradle bootRun`

## API can be found at:

http://localhost:8080/swagger-ui/index.html

## Running tests
JUnit tests needs to be run with Intellij rather than gradle in `Build tools -> Gradle -> build and run` settings 