# Advertising Platform - Notes API

This project is a simple REST API for managing notes. The project is built using Maven and allows storing notes in two different ways: either in an in-memory database or in a file. You can choose the storage method by selecting the appropriate Maven profile.

## Features

- Create, read, update, and delete (CRUD) operations for notes
- Two storage options:
    - **In-Memory Database (H2)**: Stores notes temporarily in memory (data is lost when the application stops)
    - **File Storage (JSON)**: Persists notes to a file on disk

## Prerequisites

- Java 21 or higher
- Spring Boot 3.3.3 or higher
- Maven 3.9.7 or higher

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/j-tino/advertising-platform.git
    cd advertising-platform
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

## Configuration

### In-Memory Database
When using the in-memory database profile, the application stores all notes in memory, meaning all data will be lost when the application stops. For this project, the H2 database is used as the in-memory database.

You can access the database configurations in the `application-database.properties` file. To inspect the data directly, you can navigate to the H2 console at http://localhost:8080/h2-console while the application is running.

### File Storage
When using the file storage profile, notes are saved to a JSON file on disk. Upon starting the application, a `generated` directory will be created in the directory where the app was launched. This directory will contain the JSON file that stores the notes.

## Running the Application

You can run the application using Maven or by deploying the JAR file with Java 21. To deploy the JAR file, navigate to the target/ directory where the JAR file is generated. You can either run the file directly from this directory or copy it to another location for execution.
### In-Memory Database

To run the application using the in-memory database:

- Using Maven:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=database
    ```
- Using the JAR file:
    ```bash
    java -jar AdvertisingPlatform-0.0.1-SNAPSHOT.jar --spring.profiles.active=database
    ```

### File Storage

To run the application using file storage:

- Using Maven:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=file
    ```
- Using the JAR file:
    ```bash
    java -jar AdvertisingPlatform-0.0.1-SNAPSHOT.jar --spring.profiles.active=file
    ```

## API Endpoints
These are the endpoints available in this project for managing notes. You can test these endpoints using tools like Postman, cURL, or any other API testing tool.

- **GET /notes**: Retrieve all notes
- **GET /notes/{id}**: Retrieve a specific note by ID 
- **POST /notes**: Create a new note
  ```JSON 
  { 
    "title": "Note Title", 
    "body": "Note Body" 
  }
  ```
- **PUT /notes/{id}**: Update an existing note by ID
    ```JSON 
    { 
      "title": "Note Title EDITED", 
      "body": "Note Body EDITED" 
    }
    ```
- **DELETE /notes/{id}**: Delete a note by ID


## Tests

   ```bash
   mvn test 
   ```


