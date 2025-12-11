# Number-Guessing-Game-Spring-Boot---Thymeleaf-MYSQL-

# Description :
The Number Guessing Game challenges users to guess a random number between 1 and 100.
Each game is saved in the database with the number of attempts made and timestamps.
Users can view their personal game history and see how they rank on the leaderboard.

# Setup Instructions :
## Requirements :

1. Java JDK 25
2. Maven
3. WAMP or XAMPP (for MySQL database)
4. IDE such as IntelliJ IDEA or Eclipse

## Downloading Project
Option A — Clone from GitHub

1. git clone https://github.com/annannkl/Number-Guessing-Game-Spring-Boot---Thymeleaf-MYSQL-.git
2. cd guessgame

Option B — Download ZIP

1. Click the green “Code” button on GitHub → “Download ZIP”.
2. Extract it to a folder (e.g., C:\Users\YourName\Documents\guessgame).

# Database Setup (MySQL)

1. Open phpMyAdmin (through WAMP or XAMPP).
2. Create a new database named:

CREATE DATABASE guessgame;

3. Make sure your MySQL server is running.
4. Check that your configuration in
   src/main/resources/application.properties looks like this:

spring.datasource.url=jdbc:mysql://localhost:3306/guessgame?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.thymeleaf.cache=false
server.port=8080

# Running the Application

Option A — Using IDE:

1. Open the project in IntelliJ or Eclipse.
2. Run the class:

com.example.guessgame.GuessgameApplication

Option B — Using Terminal:

1. mvn spring-boot:run
2. Once it starts successfully, open your browser and go to:

http://localhost:8080

# Technologies Used

1. Spring Boot (Backend Framework)
2. Thymeleaf (Template Engine)
3. Bootstrap 5 (Frontend Styling)
4. MySQL (Database)
5. Hibernate / JPA (ORM)
6. Java 25+

Web Programming Project — November 2025
Creator : Tzavara Anna
