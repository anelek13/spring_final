package com.example.schoolapp.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "subjects")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_subjects",
        initialValue = 1,
        allocationSize = 1
)
public class Subject extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

}
