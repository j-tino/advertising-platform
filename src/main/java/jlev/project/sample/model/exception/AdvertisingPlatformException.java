package jlev.project.sample.model.exception;

public class AdvertisingPlatformException extends RuntimeException {

  public AdvertisingPlatformException() {
  }

  public AdvertisingPlatformException(String message) {
    super(message);
  }

  public AdvertisingPlatformException(String message, Long id) {
    super(message);
  }

}
