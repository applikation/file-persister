package com.task.file.persister.serviceimpl;

import com.task.file.persister.entity.Records;
import com.task.file.persister.service.IParserService;
import com.task.file.persister.service.IPersistService;
import com.task.file.persister.repo.IRecordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PersistenceService implements IPersistService {

    private final IParserService parserService;
    private final IRecordsRepository recordsRepository;

    @Autowired
    public PersistenceService(IParserService parserService, IRecordsRepository recordsRepository) {
        this.parserService = parserService;
        this.recordsRepository = recordsRepository;
    }


    @Override
    public int save(String fileData) {
        List<Records> recordsList = parserService.parse(fileData);
        recordsRepository.saveAll(recordsList);
        log.debug("{} Records saved: {}", recordsList.size(), recordsList);
        return recordsList.size();
    }
}
