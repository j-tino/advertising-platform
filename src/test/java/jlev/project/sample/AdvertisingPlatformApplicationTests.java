package jlev.project.sample;

import jlev.project.sample.services.NotesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("database")
class AdvertisingPlatformApplicationTests {

  @Autowired
  private NotesService notesService;

  @Test
  void contextLoads() {
    Assertions.assertNotNull(notesService);
  }

}
