package com.mjc.school.service.exceptions;

import java.io.Serial;

public class ValidatorException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public ValidatorException(String message) {
    super(message);
  }
}
