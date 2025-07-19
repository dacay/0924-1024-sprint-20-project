package tr.com.workintech.s20.app.exception;

public class AuthenticationException extends RuntimeException {

  public AuthenticationException(String message) {
    super(message);
  }
}
