package com.task.file.persister.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidFileException extends RuntimeException{

    public InvalidFileException(String message) {
        super(message);
    }
}
