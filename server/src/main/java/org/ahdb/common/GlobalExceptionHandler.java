package org.ahdb.common;

public class GlobalExceptionHandler {

//    @ExceptionHandler(AhdbUserException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
    public ErrorResponse handleException(Exception ex) {
        ErrorResponse err = new ErrorResponse();

        if (ex instanceof AhdbUserException) {
            err.setMessage(ex.getMessage());
        }

        return err;
    }

}
