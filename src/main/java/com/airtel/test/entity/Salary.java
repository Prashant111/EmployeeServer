package com.airtel.test.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "salaries")
public class Salary {
    @Id
    @Column(name = "salary_plan", nullable = false)
    private String salaryPlan;
    @Column(name = "net_salary", nullable = false)
    private double netSalary;
    @Column(name = "variable_salary")
    private double variableSalary;
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;
}
