package org.ahdb.common;

public class ErrorResponse {

     public String message;

     public String getMessage() {
          return message;
     }

     public ErrorResponse setMessage(String message) {
          this.message = message;
          return this;
     }
}
