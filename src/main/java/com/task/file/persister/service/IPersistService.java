package com.task.file.persister.service;

/**
 * Interface to persist/save file data into the underlying DB/store based on implementation.
 */
public interface IPersistService {
    int save(String fileData);
}
