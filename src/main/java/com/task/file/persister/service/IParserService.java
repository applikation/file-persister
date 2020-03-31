package com.task.file.persister.service;

import com.task.file.persister.entity.Records;

import java.util.List;

/**
 * Interface to define parsing algorithm to be used.
 */
public interface IParserService {
    List<Records> parse(String fileData);
}
