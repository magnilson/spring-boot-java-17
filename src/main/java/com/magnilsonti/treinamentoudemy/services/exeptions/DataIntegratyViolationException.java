package com.magnilsonti.treinamentoudemy.services.exeptions;

public class DataIntegratyViolationException extends RuntimeException {

  public DataIntegratyViolationException(String message) {
    super(message);
  }
}
