package com.task.file.persister.service;

import com.task.file.persister.entity.Records;

/**
 * Interface to fetch/query/retrieve records from the underlying DB/store based on implementation.
 */
public interface IRetrieveService {
    Records getRecord(String id);
}
