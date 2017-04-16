package com.cgi.dentistapp.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dentist_name")
    private String dentistName;

    @Column(name = "physician_name")
    private String physicianName;

    @Column(name = "visit_date_time")
    private Timestamp visitDateTime;

    public DentistVisitEntity(String dentistName, String physicianName, Timestamp visitDateTime) {
        this.dentistName = dentistName;
        this.physicianName = physicianName;
        this.visitDateTime = visitDateTime;
    }
}
