package pt.ua.deti.tqs.covidinfoapi.exception;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;

import java.util.Date;

@Generated
@AllArgsConstructor
@Getter
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;

}