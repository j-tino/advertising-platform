package jlev.project.sample.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BadRequestException extends AdvertisingPlatformException {
   private String title;
   private String detail;

   public BadRequestException(String title, String detail) {
      super(String.format("%s: %s", title, detail));
      this.title = title;
      this.detail = detail;
   }
}
