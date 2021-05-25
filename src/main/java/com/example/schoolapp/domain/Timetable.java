package com.example.schoolapp.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "timetables")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_timetables",
        initialValue = 1,
        allocationSize = 1
)
public class Timetable extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Subject subject;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private User student;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private User educator;
    private String startTime;
    private String finishTime;
}
