package jlev.project.sample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class FileStorageConfiguration {

      @Bean
      @Profile("file")
      /**
       * Generate a directory for the generated note files
       */
      public Path generateDirectory() throws IOException {
         Path currentDir = Paths.get("").toAbsolutePath();

         String generatedDir = "generated";

         Path generatedPath = currentDir.resolve(generatedDir);

         if (!Files.exists(generatedPath)) {
            Files.createDirectories(generatedPath);
         }
         log.info("=======================================");
         log.info("Generated directory: {}", generatedPath);
         log.info("=======================================");
         return generatedPath;
      }
}
