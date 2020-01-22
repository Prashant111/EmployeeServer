package com.airtel.test.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;
}
