package com.example.schoolapp.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "calendars")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_calendars",
        initialValue = 1,
        allocationSize = 1
)
public class Calendar extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "calendar_id")
    private Long id;
    private String dayOfWeek;
    private int weekNumber;
}
