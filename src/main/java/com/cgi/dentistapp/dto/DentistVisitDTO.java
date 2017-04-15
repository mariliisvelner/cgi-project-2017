package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by serkp on 2.03.2017.
 */
public class DentistVisitDTO {

    @Size(min = 1, max = 50)
    private String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date visitDate;

    @Size(min = 1, max = 50)
    private String physicianName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime visitDateTime;

    public LocalDateTime getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(LocalDateTime visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

    public DentistVisitDTO() {
    }

    public DentistVisitDTO(String dentistName, Date visitDate, String physicianName, LocalDateTime visitDateTime) {
        this.dentistName = dentistName;
        this.visitDate = visitDate;
        this.physicianName = physicianName;
        this.visitDateTime = visitDateTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}
