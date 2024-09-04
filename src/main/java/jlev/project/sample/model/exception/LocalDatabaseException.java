package jlev.project.sample.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class LocalDatabaseException extends RuntimeException {
   private String title;
   private String detail;

   public LocalDatabaseException(String title, String detail) {
      super(String.format("%s: %s", title, detail));
      this.title = title;
      this.detail = detail;
   }

   public LocalDatabaseException(String message) {
      super(message);
   }
}
