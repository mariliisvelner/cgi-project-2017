package com.cgi.dentistapp.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dentist_name")
    private String dentistName;

    @Column(name = "visit_date")
    private Date visitDate;

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

    public DentistVisitEntity(String dentistName, Date visitDate, String physicianName, Timestamp visitDateTime) {
        this.setDentistName(dentistName);
        this.setVisitDate(visitDate);
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

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

//    public String toString(){
//        return "DentistVisitEntity(" + this.getId().toString() + ", "
//                                     + this.getDentistName() + ", "
//                                     + this.getVisitDate().toString() + ", "
//                                     + this.getPhysicianName() + ", "
//                                     + this.getVisitDateTime().toString() +")";
//    }
}
