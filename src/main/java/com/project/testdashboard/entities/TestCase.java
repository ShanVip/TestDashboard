package com.project.testdashboard.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TestCases")
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String stepsToPlay;

    private String expectedResult;

    private String realResult;

}
