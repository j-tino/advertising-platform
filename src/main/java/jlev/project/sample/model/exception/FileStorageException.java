package jlev.project.sample.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class FileStorageException extends AdvertisingPlatformException {
  private String title;
  private String detail;

  public FileStorageException(String title, String detail) {
    super(String.format("%s: %s", title, detail));
    this.title = title;
    this.detail = detail;
  }

  public FileStorageException(String message) {
    super(message);
  }
}
