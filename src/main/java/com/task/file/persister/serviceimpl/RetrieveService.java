package com.task.file.persister.serviceimpl;

import com.task.file.persister.entity.Records;
import com.task.file.persister.exceptions.NoRecordsFoundException;
import com.task.file.persister.repo.IRecordsRepository;
import com.task.file.persister.service.IRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RetrieveService implements IRetrieveService {

    private final IRecordsRepository recordsRepository;

    @Autowired
    public RetrieveService(IRecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    @Override
    public Records getRecord(String id) {
        return recordsRepository.findById(id).orElseThrow(NoRecordsFoundException::new);
    }
}
