package com.task.file.persister.serviceimpl;

import com.task.file.persister.repo.IRecordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteServiceTest {

    @Mock
    private IRecordsRepository recordsRepository;

    @InjectMocks
    DeleteService deleteService;

    @Test
    public void testRecordsDeleted() {
        deleteService.delete("1");

        verify(recordsRepository, times(1)).deleteById("1");
    }


}