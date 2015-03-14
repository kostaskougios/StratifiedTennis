# StratifiedTennis

- To start the server within an IDE, run class Application

- Swagger url is

http://localhost:8080/swagger/index.html

- I am using an in-memory H2 database

- package-info.java contains a brief of some packages

- I didn't apply any security for game id's. They are numbers which can be easily guessed. (A more secure solution would i.e. be those to be GUID's)

- domain model classes are immutable and marked as @Immutable

- to simplify things, the tennis game in this impl consists of 2 games no matter who wins them. A game is won when a players score is > 4 and
at least 2 points higher than the other players score.