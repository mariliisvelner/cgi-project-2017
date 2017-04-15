package com.cgi.dentistapp.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
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

    public Timestamp getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(Timestamp visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public DentistVisitEntity() {
    }

    public DentistVisitEntity(String dentistName, String physicianName, Timestamp visitDateTime) {
        this.setDentistName(dentistName);
        this.setPhysicianName(physicianName);
        this.setVisitDateTime(visitDateTime);
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

}
