package com.task.file.persister.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Records {

    @Id
    private String primaryKey;

    private String name;
    private String description;
    private LocalDateTime updatedTimeStamp;
}
