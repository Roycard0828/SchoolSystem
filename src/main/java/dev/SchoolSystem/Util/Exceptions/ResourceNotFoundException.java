package dev.SchoolSystem.Util.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String className, String message){
        super(className + ": " + message);
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

}
