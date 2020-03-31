package com.task.file.persister.service;

/**
 * Interface to delete records from the underlying DB/store based on implementation, using primary key/id.
 */
public interface IDeleteService {
    void delete(String id);
}
