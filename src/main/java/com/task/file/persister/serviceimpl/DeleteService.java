package com.task.file.persister.serviceimpl;

import com.task.file.persister.service.IDeleteService;
import com.task.file.persister.repo.IRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteService implements IDeleteService {


    private final IRecordsRepository recordsRepository;

    @Autowired
    public DeleteService(IRecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    @Override
    public void delete(String id) {
        recordsRepository.deleteById(id);
    }
}
