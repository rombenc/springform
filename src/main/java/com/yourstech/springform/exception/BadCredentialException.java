package com.yourstech.springform.exception;

public class BadCredentialException extends RuntimeException {
  public BadCredentialException(String message, int status) {
    super(message);
  }
}
