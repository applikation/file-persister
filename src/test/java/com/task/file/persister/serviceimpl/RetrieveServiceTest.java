package com.task.file.persister.serviceimpl;

import com.task.file.persister.entity.Records;
import com.task.file.persister.exceptions.NoRecordsFoundException;
import com.task.file.persister.repo.IRecordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveServiceTest {

    @Mock
    private IRecordsRepository recordsRepository;

    @InjectMocks
    RetrieveService retrieveService;

    @Test
    public void testRecordsFound() {
        String name = "name";
        String pk = "1";
        Records expected = new Records(pk, name, "desc", LocalDateTime.now());
        expected.setPrimaryKey(pk);
        when(recordsRepository.findById(pk)).thenReturn(Optional.of(expected));

        Records actual = retrieveService.getRecord("1");

        verify(recordsRepository, times(1)).findById(pk);
        assertEquals(name, actual.getName());
        assertEquals(pk, actual.getPrimaryKey());
    }

    @Test(expected = NoRecordsFoundException.class)
    public void testRecordsNotFound() {
        retrieveService.getRecord("1");
    }

}