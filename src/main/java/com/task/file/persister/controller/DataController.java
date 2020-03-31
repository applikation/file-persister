package com.task.file.persister.controller;

import com.task.file.persister.entity.Records;
import com.task.file.persister.exceptions.InvalidFileException;
import com.task.file.persister.exceptions.InvalidHeaderException;
import com.task.file.persister.exceptions.NoRecordsFoundException;
import com.task.file.persister.service.IDeleteService;
import com.task.file.persister.service.IPersistService;
import com.task.file.persister.service.IRetrieveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;


@Slf4j
@RestController
@RequestMapping("/data")
public class DataController {

    private final IPersistService persistService;

    private final IDeleteService deleteService;

    private final IRetrieveService retrieveService;

    @Autowired
    public DataController(IPersistService persistService, IDeleteService deleteService, IRetrieveService retrieveService) {
        this.persistService = persistService;
        this.deleteService = deleteService;
        this.retrieveService = retrieveService;
    }

    @PostMapping("/save")
    public ResponseEntity<Records> saveRecords(@RequestBody String fileData) {
        int numOfRows = persistService.save(fileData);
        return new ResponseEntity(numOfRows + " Rows created/updated", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable String id) {
        deleteService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Records> getRecord(@PathVariable String id) {
        return ResponseEntity.ok(retrieveService.getRecord(id));
    }


    @ExceptionHandler({InvalidFileException.class, InvalidHeaderException.class,
            EmptyResultDataAccessException.class, NoRecordsFoundException.class,
            DateTimeException.class, DateTimeParseException.class})
    public ResponseEntity handleException(Exception ex) {
        log.error("Bad Request", ex);
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
