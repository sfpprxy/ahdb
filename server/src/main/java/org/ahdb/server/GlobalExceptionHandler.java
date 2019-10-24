package org.ahdb.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AhdbUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(Exception ex) {
        ErrorResponse err = new ErrorResponse();

        if (ex instanceof AhdbUserException) {
            err.setMessage(ex.getMessage());
        }

        return err;
    }

}
