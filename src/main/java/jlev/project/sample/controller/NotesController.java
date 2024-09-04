package jlev.project.sample.controller;

import jlev.project.sample.model.Notes;
import jlev.project.sample.model.exception.BadRequestException;
import jlev.project.sample.model.exception.FileStorageException;
import jlev.project.sample.model.exception.LocalDatabaseException;
import jlev.project.sample.model.exception.MissingPathException;
import jlev.project.sample.services.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(NotesController.REQUEST_MAPPING)
public class NotesController {

      protected static final String REQUEST_MAPPING = "/notes";
      private final NotesService notesService;

      @PostMapping()
      public ResponseEntity<Object> create(@RequestBody Notes notes) {
         if (notes.getTitle() == null || notes.getTitle().isEmpty() || notes.getBody() == null || notes.getBody().isEmpty()) {
            throw new BadRequestException("Invalid Input", "Title or Body cannot be empty");
         }

         try {
            Notes newNotes = notesService.add(notes);
            return new ResponseEntity<>(newNotes, HttpStatus.CREATED);
         } catch (FileStorageException e) {
            throw new FileStorageException(e.getTitle(), e.getDetail());
         }
      }

      @GetMapping()
      public ResponseEntity<List<Notes>> findAll() {
         List<Notes> notes = notesService.findAll();

         return new ResponseEntity<>(notes, HttpStatus.ACCEPTED);
      }

      @GetMapping("/{id}")
      public ResponseEntity<Object> findById(@PathVariable Long id) {
         if (id == null) {
            throw new MissingPathException("URL does not match expected", "Missing note id");
         }

         try {
            Optional<Notes> notes = notesService.find(id);
            if (notes.isPresent()) {
               return new ResponseEntity<>(notes.get(), HttpStatus.OK);
            } else {
               throw new LocalDatabaseException("Note Doesnt Exists", "Could not find note with id " + id);
            }
         } catch (FileStorageException e) {
            throw new FileStorageException(e.getTitle(), e.getDetail());
         }
      }

      @PutMapping(value = "/{id}")
      public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Notes updatedNote) {
         if (id == null) {
            throw new MissingPathException("URL does not match expected", "Missing note id");
         }

         if (updatedNote.getTitle() == null || updatedNote.getTitle().isEmpty() || updatedNote.getBody() == null || updatedNote.getBody().isEmpty()) {
            throw new BadRequestException("Invalid Input", "Title or Body cannot be empty");
         }

         try {
            Notes notes = notesService.update(id, updatedNote);
            return new ResponseEntity<>(notes, HttpStatus.OK);
         } catch (FileStorageException e) {
            throw new FileStorageException(e.getTitle(), e.getDetail());
         } catch (LocalDatabaseException e) {
            throw new LocalDatabaseException(e.getTitle(), e.getDetail());
         }
      }

      @DeleteMapping(value = "/{id}")
      public ResponseEntity<Object> delete(@PathVariable Long id) {
         if (id == null) {
            throw new MissingPathException("URL does not match expected", "Missing note id");
         }

         try {
            notesService.delete(id);
            return new ResponseEntity<>("Successfully deleted the note.", HttpStatus.NO_CONTENT);
         } catch (FileStorageException e) {
            throw new FileStorageException(e.getTitle(), e.getDetail());
         } catch (LocalDatabaseException e) {
            throw new LocalDatabaseException(e.getTitle(), e.getDetail());
         }
      }
}
