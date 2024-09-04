package jlev.project.sample.services;

import jlev.project.sample.model.Notes;
import jlev.project.sample.model.exception.LocalDatabaseException;
import jlev.project.sample.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Profile("database")
@Service
@Slf4j
@RequiredArgsConstructor
public class H2NotesService implements NotesService {

   private final NotesRepository notesRepository;

   @Override
   public Notes add(Notes note) {
      Notes newNotes = new Notes();
      newNotes.setTitle(note.getTitle());
      newNotes.setBody(note.getBody());

      return notesRepository.save(newNotes);
   }

   @Override public List<Notes> findAll() {
      return notesRepository.findAll();
   }

   @Override
   public Optional<Notes> find(Long id) {
      return notesRepository.findById(id);
   }

   @Override
   public Notes update(Long id, Notes updatedNote) {
      Notes note = notesRepository.findById(id).orElse(null);
      if (Objects.nonNull(note)) {
         note.setTitle(updatedNote.getTitle());
         note.setBody(updatedNote.getBody());
         note = notesRepository.save(note);
      } else {
         throw new LocalDatabaseException("Note Doesn't Exists", "ID: "+ id +" doesn't exists in the database");
      }
      return note;
   }

   @Override
   public void delete(Long id) {
      Notes note = notesRepository.findById(id).orElse(null);
      if (Objects.nonNull(note)) {
         notesRepository.delete(note);
      } else {
         throw new LocalDatabaseException("Note Doesn't Exists", "ID: "+ id +" doesn't exists in the database");
      }
   }
}
