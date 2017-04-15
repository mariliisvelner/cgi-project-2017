package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        DentistVisitEntity visit = new DentistVisitEntity(dentistVisitDTO.getDentistName(),
                                                          dentistVisitDTO.getVisitTime(),
                                                          dentistVisitDTO.getPhysicianName());
        dentistVisitDao.create(visit);
    }

    public List<DentistVisitEntity> listVisits () {
        return dentistVisitDao.getAllVisits();
    }

}
