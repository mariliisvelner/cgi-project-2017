package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DentistVisitService {

    private final DentistVisitDao dentistVisitDao;

    @Autowired
    public DentistVisitService(DentistVisitDao dentistVisitDao) {
        this.dentistVisitDao = dentistVisitDao;
    }

    public void addVisit(DentistVisitDTO dentistVisitDTO) {
        System.out.println(dentistVisitDTO.getVisitDateTime());
        DentistVisitEntity visit = new DentistVisitEntity(dentistVisitDTO.getDentistName(),
                                                          dentistVisitDTO.getVisitDate(),
                                                          dentistVisitDTO.getPhysicianName(),
                                                          localDateTimeToTimestamp(dentistVisitDTO.getVisitDateTime()));
        System.out.println(visit.getId());
        System.out.println(visit.getDentistName());
        System.out.println(visit.getVisitDate());
        System.out.println(visit.getVisitDateTime());
        System.out.println(visit.getPhysicianName());
        dentistVisitDao.create(visit);
    }

    public List<DentistVisitEntity> listVisits () {
        return dentistVisitDao.getAllVisits();
    }

    public Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime){
        return Timestamp.valueOf(localDateTime);
    }

}
