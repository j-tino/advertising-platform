package jlev.project.sample.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MissingPathException extends AdvertisingPlatformException {
   private String title;
   private String detail;

   public MissingPathException(String title, String detail) {
      super(String.format("%s: %s", title, detail));
      this.title = title;
      this.detail = detail;
   }
}
