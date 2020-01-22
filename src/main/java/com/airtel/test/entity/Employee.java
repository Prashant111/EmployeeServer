package com.airtel.test.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "department_id", nullable = false)
    private String departmentId;
    @Column(name = "salary_plan", nullable = false)
    private String salaryPlan;
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;
}
