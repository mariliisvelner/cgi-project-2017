package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.SearchQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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
                                                          dentistVisitDTO.getPhysicianName(),
                                                          Timestamp.valueOf(dentistVisitDTO.getVisitDateTime()));
        dentistVisitDao.create(visit);
    }

    public List<DentistVisitDTO> listVisits () {
        return dentistVisitDao.getAllVisits().stream()
                .map(e -> new DentistVisitDTO(
                        e.getDentistName(),
                        e.getPhysicianName(),
                        e.getVisitDateTime().toLocalDateTime()
                ))
                .collect(Collectors.toList());
    }

    public List<DentistVisitDTO> getSearchResults(SearchQueryDTO searchQueryDTO){
        return dentistVisitDao.getSearchResults(searchQueryDTO).stream()
                .map(e -> new DentistVisitDTO(
                        e.getDentistName(),
                        e.getPhysicianName(),
                        e.getVisitDateTime().toLocalDateTime()
                ))
                .collect(Collectors.toList());
    }


}
