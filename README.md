
## A Story of Passionate Software Engineer
You have an idea develop a website which allow users to store and share GPS track online (similar to http://www.trackprofiler.com/track/index). After discussion with your team, they helped you to came up with some mock-up files.
Front-end side will be developed by another team member. You are the only one who is going to be in charge of the backend service development.

Because you are so excited to show the idea to your CEO, you decided to reduce the scope and focus one developing 1 WS API with three endpoints:

- An endpoint that allow users to upload "gpx" file and store mandatory information such as "metadata, waypoint, track"
- An endpoint to return a list of "Latest track" from our users
- An endpoint to allow users to view details of their gpx file

Although this is a prototype version, but you are a professional software engineer. You don't allow yourself to code without a System Diagram or Workflow Diagram, or produce "dirty-code" and code without Unit Tests. Additionally, since this is a fairly small and simple project, you are not allowed to use the Lombok library.

Once your have completed your solution, please upload them to Github.

This is all you have right now:

- https://en.wikipedia.org/wiki/GPS_Exchange_Format
- Mock-up files
- A sample gpx file
- A passionate heart, if you don't like the given mock-up files, feel free to change and show your CEO a better version
- Your team is a big fan of "Spring IO" tech stack, so they prefers you use Sprint Boot as a starting point
- An in-memory database is enough for this moment (H2)

---

## The Result of Passionate Software Engineer
### API endpoints
```
POST   /api/gps/upload                  allow users to upload "gpx" file and store mandatory information
GET    /api/gps/latest?page=x&size=y    return a list of "Latest track" from our users
GET    /api/gps/{id}                    view details of users' gpx files
```

### How to build and run project
#### using gradle
```
$ cd track-challenge
$ ./gradlew clean
$ ./gradlew xjc
$ ./gradlew assemble
$ ./gradlew run
```

To perform automation tests:
```
$ ./gradlew clean
$ ./gradlew xjc
$ ./gradlew test
```

#### using maven
```
$ cd track-challenge
$ mvn clean
$ mvn install
$ java -jar target/demo-0.0.1-SNAPSHOT.jar
```

To perform automation tests:
```
$ mvn test
```

* IMPORTANT NOTE: Maven and Gradle conflict! Before executing any `mvn` commands, please make sure there is no source code generated from gradle. Use `./gradlew clean` to remove all generated files from gradle build.

### Manual testing
using curl
```
$ curl -X POST localhost:8080/api/gps/upload -H "content-type:text/xml" --data @src/test/resources/sample-test-small.gpx
$ curl -X GET 'localhost:8080/api/gps/latest?page=0&size=10'
$ curl -X GET 'localhost:8080/api/gps/-1'
```

### H2 console
To enable H2 web console, go to `application.properties` and uncomment the property `spring.h2.console.enabled=true`

Visit console at: http://localhost:8080/h2-console

DB connection information (base on `application.properties`)
```
jdbc url=jdbc:h2:mem:gpsdb
username=sa
password=
```
