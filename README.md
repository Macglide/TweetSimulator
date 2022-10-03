# TwitterFeed

Program that simulates a twitter-like feed.Program will receive two seven-bit ASCII files. The first file contains a list of users and their followers. The second file contains tweets. Given the users, followers and tweets, the objective is to display a simulated twitter feed for each user to the console.

The program should be well designed, handle errors and should be of sufficient quality to run on a production system. Indicate all assumptions made in completing the assignment.

Each line of a well-formed user file contains a user, followed by the word 'follows' and then a comma separated list of users they follow. Where there is more than one entry for a user, consider the union of all these entries to determine the users they follow.

Lines of the tweet file contain a user, followed by greater than, space and then a tweet that may be at most 140 characters in length. The tweets are considered to be posted by the each user in the order they are found in this file.

Your program needs to write console output as follows. For each user / follower (in alphabetical order) output their name on a line. Then for each tweet, emit a line with the following format: @user: message.

Here is an example. Given user file named user.txt:

Ward follows Alan

Alan follows Martin

Ward follows Martin, Alan

And given tweet file named tweet.txt:

Alan> If you have a procedure with 10 parameters, you probably missed some.

Ward> There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.

Alan> Random numbers should not be generated with a method chosen at random.

So invoking the program with user.txt and tweet.txt as arguments should produce the following console output:

Alan

@Alan: If you have a procedure with 10 parameters, you probably missed some.

@Alan: Random numbers should not be generated with a method chosen at random.

Martin

Ward

@Alan: If you have a procedure with 10 parameters, you probably missed some.

@Ward: There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.

@Alan: Random numbers should not be generated with a method chosen at random.

# TwitterSimulator

This application was generated using JHipster 7.9.2, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v7.9.2](https://www.jhipster.tech/documentation-archive/v7.9.2).

# Instructions on how to run the program

Start the application in the dev profile, (below command). It will start on localhost:8081.
Do a POST localhost:8081/api/twitter-feed call, with content-type multipart/form-data.
Provide below parameters.

```
./mvnw
```

```
Parameters to provide when making POST localhost:8081/api/twitter-feed call.
userFile = <filename>.txt, tweetFile = <filename>.txt
```

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

In the project root, JHipster generates configuration files for tools like git, prettier, eslint, husky, and others that are well known and you can find references in the web.

`/src/*` structure follows default Java structure.

- `.yo-rc.json` - Yeoman configuration file
  JHipster configuration is stored in this file at `generator-jhipster` key. You may find `generator-jhipster-*` for specific blueprints configuration.
- `.yo-resolve` (optional) - Yeoman conflict resolver
  Allows to use a specific action when conflicts are found skipping prompts for files that matches a pattern. Each line should match `[pattern] [action]` with pattern been a [Minimatch](https://github.com/isaacs/minimatch#minimatch) pattern and action been one of skip (default if ommited) or force. Lines starting with `#` are considered comments and are ignored.
- `.jhipster/*.json` - JHipster entity configuration files
- `/src/main/docker` - Docker configurations for the application and services that the application depends on

## Development

To start this application in the dev profile, run:

```
./mvnw
```

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### JHipster Control Center

JHipster Control Center can help you manage and control your application(s). You can start a local control center server (accessible on http://localhost:7419) with:

```
docker-compose -f src/main/docker/jhipster-control-center.yml up
```

### Doing API-First development using openapi-generator-cli

[OpenAPI-Generator]() is configured for this application. You can generate API code from the `src/main/resources/swagger/api.yml` definition file by running:

```bash
./mvnw generate-sources
```

Then implements the generated delegate classes with `@Service` classes.

To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker-compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).

Refer to [Doing API-First development][] for more details.

## Building for production

### Packaging as jar

To build the final jar and optimize the TwitterSimulator application for production, run:

```
./mvnw -Pprod clean verify
```

To ensure everything worked, run:

```
java -jar target/*.jar
```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
npm run java:docker
```

Or build a arm64 docker image when using an arm64 processor os like MacOS with M1 processor family running:

```
npm run java:docker:arm64
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

When running Docker Desktop on MacOS Big Sur or later, consider enabling experimental `Use the new Virtualization framework` for better processing performance ([disk access performance is worse](https://github.com/docker/roadmap/issues/7)).

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.9.2 archive]: https://www.jhipster.tech/documentation-archive/v7.9.2
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v7.9.2/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v7.9.2/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v7.9.2/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v7.9.2/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v7.9.2/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v7.9.2/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v7.9.2/setting-up-ci/
[node.js]: https://nodejs.org/
[npm]: https://www.npmjs.com/
[openapi-generator]: https://openapi-generator.tech
[swagger-editor]: https://editor.swagger.io
[doing api-first development]: https://www.jhipster.tech/documentation-archive/v7.9.2/doing-api-first-development/
