package pt.ua.deti.tqs.covidinfoapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.BadRequestException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DataFetchException.class)
    public ResponseEntity<?> dataFetchException(DataFetchException userNotFoundException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), userNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException badRequestException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), badRequestException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}