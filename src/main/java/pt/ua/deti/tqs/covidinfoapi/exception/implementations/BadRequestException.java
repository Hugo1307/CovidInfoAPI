package pt.ua.deti.tqs.covidinfoapi.exception.implementations;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
