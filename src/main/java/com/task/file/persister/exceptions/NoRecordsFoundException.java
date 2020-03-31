package com.task.file.persister.exceptions;

public class NoRecordsFoundException extends RuntimeException{

    public NoRecordsFoundException() {
        super("No corresponding record found in DB.");
    }
}
