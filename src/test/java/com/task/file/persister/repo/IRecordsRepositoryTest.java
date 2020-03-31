package com.task.file.persister.repo;

import com.task.file.persister.entity.Records;
import com.task.file.persister.exceptions.NoRecordsFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IRecordsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IRecordsRepository recordsRepository;

    @Test
    public void testFindbyIDReturnRecord() {
        // given
        Records record = new Records("10", "name", "description", LocalDateTime.now());
        entityManager.persist(record);
        entityManager.flush();

        // when
        Records found = recordsRepository.findById(record.getPrimaryKey()).get();

        // then
        assertEquals(record.getName(), found.getName());
    }

    @Test(expected = Test.None.class)
    public void testDeletebyID() {
        // given
        Records record = new Records("10", "name", "description", LocalDateTime.now());
        entityManager.persist(record);
        entityManager.flush();

        recordsRepository.deleteById(record.getPrimaryKey());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeletebyIDExceptionThrown() {
        recordsRepository.deleteById("99");
    }

    @Test
    public void testNoRecordsFound() {
        assertEquals(Optional.empty(), recordsRepository.findById("99"));
    }

}