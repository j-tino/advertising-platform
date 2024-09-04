package jlev.project.sample.controller;

import jlev.project.sample.model.Notes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("database")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotesControllerIntegrationTest {
   private final int port = 8080;

   @Autowired
   private TestRestTemplate restTemplate;

   @Test
   @Order(1)
   public void testEndpointCreated_whenUrlAndBodyIsValid_thenCreateNotes() {
      String url = "http://localhost:" + port + "/notes/";

      Notes newNote = new Notes();
      newNote.setTitle("Test Note");
      newNote.setBody("This is a test note.");

      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");

      HttpEntity<Notes> entity = new HttpEntity<>(newNote, headers);

      ResponseEntity<Notes> response = restTemplate.exchange(url, HttpMethod.POST, entity, Notes.class);

      Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
      Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getTitle(), "Test Note");
      Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getBody(),"This is a test note.");
   }

   @Test
   @Order(2)
   public void testEndpointGetById_whenUrlIdIsValid_thenReturnNotes() {
      String url = "http://localhost:" + port + "/notes/1";

      ResponseEntity<Notes> response = restTemplate.exchange(url, HttpMethod.GET, null, Notes.class);

      Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
      Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getTitle(), "Test Note");
      Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getBody(),"This is a test note.");
   }

   @Test
   @Order(3)
   public void testEndpointGet_whenUrlIsValid_thenReturnAllNotes() {
      String url = "http://localhost:" + port + "/notes/";

      ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

      Assertions.assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
      Assertions.assertNotNull(Objects.requireNonNull(response.getBody()));
   }

   @Test
   @Order(4)
   public void whenEndpointIsUpdate_thenEditNotes() {
      String url = "http://localhost:" + port + "/notes/1";

      Notes updatedNote = new Notes();
      updatedNote.setTitle("Updated Note Title");
      updatedNote.setBody("Updated Note Content");

      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");

      HttpEntity<Notes> entity = new HttpEntity<>(updatedNote, headers);

      ResponseEntity<Notes> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Notes.class);
      Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
      Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getTitle(), "Updated Note Title");
      Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getBody(),"Updated Note Content");
   }

   @Test
   @Order(5)
   public void testEndpointDelete_whenUrlAndIdIsValid_thenNotesDeleted() {
      String url = "http://localhost:" + port + "/notes/1";

      ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);

      Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
   }

   //NEGATIVE TESTS
   @Test
   public void testEndpointCreated_whenUrlAndBodyIsiNValid_thenThrowException() {
      String url = "http://localhost:" + port + "/notes/";

      Notes newNote = new Notes();
      newNote.setTitle(null);
      newNote.setBody(null);

      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");

      HttpEntity<Notes> entity = new HttpEntity<>(newNote, headers);

      ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

      Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
   }

   @Test
   public void testEndpointGetById_whenUrlIdIsValid_thenThrowException() {
      String url = "http://localhost:" + port + "/notes/50";

      ResponseEntity<Notes> response = restTemplate.exchange(url, HttpMethod.GET, null, Notes.class);

      Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
   }

   @Test
   public void testEndpointUpdate_whenUrlAndIdIsInvalid_thenThrowException() {
      String url = "http://localhost:" + port + "/notes/77";

      Notes updatedNote = new Notes();
      updatedNote.setTitle("Updated Note Title");
      updatedNote.setBody("Updated Note Content");

      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");

      HttpEntity<Notes> entity = new HttpEntity<>(updatedNote, headers);

      ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
      Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
   }

   @Test
   public void testEndpointDelete_whenUrlAndIdIsInvalid_thenThrowException() {
      String url = "http://localhost:" + port + "/notes/25";

      ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);

      Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
   }
}
