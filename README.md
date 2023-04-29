## PumpPal Fitness App
PumpPal is a fitness tracking application that allows users to log and track their workouts over time. This back end application is responsible for managing user accounts, storing and retrieving workout data.

(Front end coming soon)

### <u>Technologies</u>:
- Spring Boot
- Spring JPA
- PostgreSQL
- Docker

### <u>How to start backend</u>:

#### Regular setup:
- Make sure the configurations  for the database url, username and password ```src/main/resources/application-properties``` correspond to your own local PostgresSQL database  
- Run the command from the project root:
>./mvnw spring-boot:run
- Import the postman collection and start testing out requests!

#### Docker setup:
- Install docker and docker-compose
- Build project:
> ./mvnw clean package -DskipTests
- Start containers:
> docker-compose up
- Stop containers:
> docker-compose down
- Remove image: 
> docker rmi docker-spring-boot-postgres:latest