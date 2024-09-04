package jlev.project.sample.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class NotesTest {

   @Test
   public void whenTestNotes_thenAssertNotNull() {
      Notes notes = new Notes();
      notes.setTitle("Title Test");
      notes.setBody("Body Test");

      Assert.hasText(notes.getTitle(), "Title should not be null");
      Assert.hasText(notes.getBody(), "Body should not be null");
   }
}
