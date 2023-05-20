package com.project.testdashboard.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "bugs")
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String stepsToPlay;

    private String expectedResult;

    private String realResult;

    private String status;

    private String priority;

    @Column(name = "created_at")
    private Date createdAt;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


}
