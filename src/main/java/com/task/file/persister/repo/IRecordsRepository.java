package com.task.file.persister.repo;

import com.task.file.persister.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecordsRepository extends JpaRepository<Records, String> {
}
