# StratifiedTennis

- JDK8 is required.

- To start the server within an IDE, run class Application

- to run all tests using maven:

mvn clean test

- Swagger url is

http://localhost:8080/swagger/index.html

- I am using an in-memory H2 database

- package-info.java contains a brief of some packages

- I didn't apply any security for game id's. They are numbers which can be easily guessed. (A more secure solution would i.e. be those to be GUID's)

- domain model classes are immutable and marked as @Immutable

- to simplify things, the tennis game in this impl consists of 2 games no matter who wins them. A game is won when a players score is > 4 and
at least 2 points higher than the other players score. This deviates from the original requirements but we've discussed this over email.


Quick description:

The app uses spring boot. It starts via com.stratified.tennis.Application class which also contains initial spring wiring config.
The H2 database is recreated each time the app starts. So it starts empty.
TennisController is the controller with the 3 end points as per the requirements. Errors are handled via validation on each end
point which then throws an exception annotated accordingly so that it gives the correct http response status.
The code has separate classes for json and for the domain model as this is a best practise. json package contains the json schema DTO's
and model package contains the domain model classes.
I've tried to avoid testing the same thing twice, i.e. TennisControllerIntegrationTest doesn't have to test all things tested in
TennisControllerUnitTest, the responsibility of TennisControllerIntegrationTest is to check if the http messages are valid.