package com.task.file.persister.serviceimpl;

import com.task.file.persister.entity.Records;
import com.task.file.persister.exceptions.InvalidFileException;
import com.task.file.persister.exceptions.InvalidHeaderException;
import com.task.file.persister.service.IParserService;
import com.task.file.persister.repo.IRecordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceServiceTest {

    @Mock
    private IRecordsRepository recordsRepository;

    @Mock
    private IParserService parserService;

    @InjectMocks
    PersistenceService persistenceService;

    @Test
    public void testRecordsSaved() {
        Records record = new Records();
        record.setPrimaryKey("1");
        when(parserService.parse(getData())).thenReturn(Arrays.asList(record));
        when(recordsRepository.saveAll(Arrays.asList(record))).thenReturn(Arrays.asList(record));

        int rowSaved = persistenceService.save(getData());

        verify(recordsRepository, times(1)).saveAll(Arrays.asList(record));
        verify(parserService, times(1)).parse(getData());
        assertEquals(1, rowSaved);
    }

    private String getData() {
        return "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMPgh\n" +
                "1, Test, Description, 2020-03-30T16:46:16.277\n" +
                " ";
    }

    @Test(expected = InvalidHeaderException.class)
    public void testInvalidHeaderExceptionThrown() {
        when(parserService.parse(getData())).thenThrow(InvalidHeaderException.class);

        persistenceService.save(getData());
    }

    @Test(expected = InvalidFileException.class)
    public void testInvalidFileExceptionThrown() {
        when(parserService.parse(getData())).thenThrow(InvalidFileException.class);

        persistenceService.save(getData());
    }


}