package com.airtel.test.entity.mongodb;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class DepartmentMongo {
    @Id
    @Indexed(unique = true)
    private String name;
    private String description;
    private String createdBy;
    private String lastModifiedBy;
}
