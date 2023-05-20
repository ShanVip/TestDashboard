package com.project.testdashboard.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "TestCases")
public class TestCase {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String description;

    private String stepsToPlay;

    private String expectedResult;

    private String realResult;

}
