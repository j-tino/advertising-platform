package jlev.project.sample.controller;

import jlev.project.sample.model.exception.BadRequestException;
import jlev.project.sample.model.exception.FileStorageException;
import jlev.project.sample.model.exception.LocalDatabaseException;
import jlev.project.sample.model.exception.MissingPathException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(BadRequestException.class)
   public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException ex) {
      Map<String, String> errorResponse = new LinkedHashMap<>();
      errorResponse.put("error", "Bad Request");
      errorResponse.put("title", ex.getTitle());
      errorResponse.put("detail", ex.getDetail());

      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(MissingPathException.class)
   public ResponseEntity<Map<String, String>> handleMissingPathException(MissingPathException ex) {
      Map<String, String> errorResponse = new LinkedHashMap<>();
      errorResponse.put("title", ex.getTitle());
      errorResponse.put("detail", ex.getDetail());

      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
   }

   @ExceptionHandler(FileStorageException.class)
   public ResponseEntity<Map<String, String>> handleFileStorageException(FileStorageException ex) {
      Map<String, String> errorResponse = new LinkedHashMap<>();
      errorResponse.put("title", ex.getTitle());
      errorResponse.put("detail", ex.getDetail());

      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(LocalDatabaseException.class)
   public ResponseEntity<Map<String, String>> handleLocalDatabaseException(LocalDatabaseException ex) {
      Map<String, String> errorResponse = new LinkedHashMap<>();
      errorResponse.put("title", ex.getTitle());
      errorResponse.put("detail", ex.getDetail());

      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
   }
}
