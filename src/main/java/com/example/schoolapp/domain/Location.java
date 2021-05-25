package com.example.schoolapp.domain;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Data
@Entity
@Table(name = "locations")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_locations",
        initialValue = 1,
        allocationSize = 1
)
public class Location extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int roomNumber;
    private String building;
}
