package com.cgi.dentistapp.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dentist_name")
    private String dentistName;

    @Column(name = "visit_time")
    private Date visitTime;

    @Column(name = "physician_name")
    private String physicianName;

    public DentistVisitEntity() {
    }

    public DentistVisitEntity(String dentistName, Date visitTime, String physicianName) {
        this.setDentistName(dentistName);
        this.setVisitTime(visitTime);
        this.setPhysicianName(physicianName);
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

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }
}
