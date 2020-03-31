package com.task.file.persister;

import com.task.file.persister.controller.DataController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilePersisterApplicationTests {

	@Autowired
	private DataController controller;

	@Test
	public void contextLoads() {
		assertNotNull(controller);
	}

}
