package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by serkp on 2.03.2017.
 */
public class DentistVisitDTO {

    @Size(min = 1, max = 50)
    private String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date visitTime;

    @Size(min = 1, max = 50)
    private String physicianName;

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

    public DentistVisitDTO() {
    }

    public DentistVisitDTO(String dentistName, Date visitTime, String physicianName) {
        this.dentistName = dentistName;
        this.visitTime = visitTime;
        this.physicianName = physicianName;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }
}
