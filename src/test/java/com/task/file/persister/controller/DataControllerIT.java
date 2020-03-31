package com.task.file.persister.controller;

import com.task.file.persister.repo.IRecordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-itest.properties")
public class DataControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IRecordsRepository repository;

    @Test
    public void testGetRecord()
            throws Exception {

        mvc.perform(get("/data/1")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("test1")));
    }

    @Test
    public void testGetInvalidRecord()
            throws Exception {

        mvc.perform(get("/data/1000")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("No corresponding record found in DB.")));
    }

    @Test
    public void testSaveRecord()
            throws Exception {

        mvc.perform(post("/data/save")
                .content("NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMP\n" +
                        "Sunny, 999, My Name, 2020-03-30T16:46:16.277\n" +
                        "  ")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(" Rows created/updated")));
    }

    @Test
    public void testSaveRecordBadRequest()
            throws Exception {

        mvc.perform(post("/data/save")
                .content("NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMP\n" +
                        "Sunny, 999, My Name, 2020-03-30T16:46:16.277\n")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Last line is not empty/blank.")));

        mvc.perform(post("/data/save")
                .content("NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMPads\n" +
                        "Sunny, 999, My Name, 2020-03-30T16:46:16.277\n" +
                        "  ")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Updated timestamp column not present in headers.")));

        mvc.perform(post("/data/save")
                .content("NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMP,INVALIDTest\n" +
                        "Sunny, 999, My Name, 2020-03-30T16:46:16.277\n" +
                        "  ")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Headers length mismatch.")));
    }

    @Test
    public void testDeleteRecord()
            throws Exception {
        String id = "5";
        mvc.perform(delete("/data/" + id)
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

        assertFalse(repository.findById(id).isPresent());
    }

    @Test
    public void testDeleteNonExistentRecord()
            throws Exception {
        mvc.perform(delete("/data/1000")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("No class com.task.file.persister.entity.Records entity with id 1000 exists!")));
    }

}