package jlev.project.sample.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jlev.project.sample.model.Notes;
import jlev.project.sample.model.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Profile("file")
@Service
@Slf4j
public class FileStorageNotesService implements NotesService {

   private static final Path GENERATED_PATH = Paths.get("").toAbsolutePath().resolve("generated") ;
   private static final String NOTES_FILE_NAME = "notes.json";
   private static final String NOTES_LAST_ID_FILE_NAME = "notes_last_id.txt";
   private final File file = GENERATED_PATH.resolve(NOTES_FILE_NAME).toFile();
   private final File lastIdFile = GENERATED_PATH.resolve(NOTES_LAST_ID_FILE_NAME).toFile();

   @Override
   public Notes add(Notes note) {
      ObjectMapper objectMapper = new ObjectMapper();
      List<Notes> notesList = new ArrayList<>();
      try {
         if (file.exists()) {
            notesList = objectMapper.readValue(file, new TypeReference<>() {});
         }

         long lastId = getLastId();
         long newId = lastId + 1;

         note.setId(newId);

         notesList.add(note);
         objectMapper.writeValue(file, notesList);

         saveLastId(newId);
      } catch (IOException e) {
         log.error(e.getMessage());
         throw new FileStorageException("File Error", e.getMessage());
      }
      return note;
   }

   @Override
   public List<Notes> findAll() {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         if (file.exists()) {
            return objectMapper.readValue(file, new TypeReference<>() {});
         } else {
            log.error("Cannot retrieve notes. {} not found", file.getName());
            throw new FileStorageException("File Error", "File not found");
         }
      } catch (IOException e) {
         log.error(e.getMessage());
         throw new FileStorageException("File Error", e.getMessage());
      }
   }

   @Override
   public Optional<Notes> find(Long id) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         if (file.exists()) {
            List<Notes> notesList = objectMapper.readValue(file, new TypeReference<>() {});
            return notesList.stream().filter(note -> Objects.equals(note.getId(), id)).findFirst();
         } else {
            log.error("Cannot find note. {} not found", file.getName());
            throw new FileStorageException("File Error", "File not found");
         }
      } catch (IOException e) {
         log.error(e.getMessage());
         throw new FileStorageException("File Error", e.getMessage());
      }
   }

   @Override
   public Notes update(Long id, Notes updateNote) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         if (file.exists()) {
            List<Notes> notesList = objectMapper.readValue(file, new TypeReference<>() {});
            Optional<Notes> note = notesList.stream().filter(n -> Objects.equals(n.getId(), id)).findFirst();
            if (note.isPresent()) {
               note.get().setTitle(updateNote.getTitle());
               note.get().setBody(updateNote.getBody());
            } else {
               throw new FileStorageException("Note Doesn't Exists", "ID: "+ id +" doesn't exists in the file");
            }

            objectMapper.writeValue(file, notesList);
         } else {
            log.error("Cannot update note. {} not found", file.getName());
            throw new FileStorageException("File Error", "File not found");
         }
      } catch (IOException e) {
         log.error(e.getMessage());
         throw new FileStorageException("File Error", e.getMessage());
      }
      return updateNote;
   }

   @Override
   public void delete(Long id) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         if (file.exists()) {
            List<Notes> notesList = objectMapper.readValue(file, new TypeReference<>() {});
            Optional<Notes> note = notesList.stream().filter(n -> Objects.equals(n.getId(), id)).findFirst();
            if (note.isPresent()) {
               notesList.remove(note.get());
               objectMapper.writeValue(file, notesList);
            } else {
               throw new FileStorageException("Note Doesn't Exists", "ID: "+ id +" doesn't exists in the file");
            }
         } else {
            log.error("Cannot delete note. {} not found", file.getName());
            throw new FileStorageException("File Error", "File not found");
         }
      } catch (IOException e) {
         log.error(e.getMessage());
         throw new FileStorageException("File Error", e.getMessage());
      }
   }

   /**
    * Retrieves the last id of note in the file
    * @return
    * @throws IOException
    */
   private long getLastId() throws IOException {
      if (!lastIdFile.exists()) {
         return 0;
      }
      String content = new String(Files.readAllBytes(Paths.get(lastIdFile.toURI())));
      return Long.parseLong(content.trim());
   }

   /**
    * Saves the last note id.
    * @param newId
    * @throws IOException
    */
   private void saveLastId(long newId) throws IOException {
      Files.write(Paths.get(lastIdFile.toURI()), String.valueOf(newId).getBytes());
   }
}
