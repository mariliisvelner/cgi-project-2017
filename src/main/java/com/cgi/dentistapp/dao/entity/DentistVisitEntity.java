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

    @Column(name = "nid")
    private String nid;

    @Column(name = "dentist_name")
    private String dentistName;

    @Column(name = "physician_name")
    private String physicianName;

    @Column(name = "visit_beginning_date_time")
    private Timestamp visitBeginningDateTime;

    @Column(name = "visit_end_date_time")
    private Timestamp visitEndDateTime;

    public DentistVisitEntity(String nid,
                              String dentistName,
                              String physicianName,
                              Timestamp visitBeginningDateTime,
                              Timestamp visitEndDateTime) {
        this.nid = nid;
        this.dentistName = dentistName;
        this.physicianName = physicianName;
        this.visitBeginningDateTime = visitBeginningDateTime;
        this.visitEndDateTime = visitEndDateTime;
    }
}
