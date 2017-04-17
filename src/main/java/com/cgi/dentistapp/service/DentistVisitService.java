package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.DetailedViewDTO;
import com.cgi.dentistapp.dto.SearchQueryDTO;
import com.cgi.dentistapp.dto.SearchQueryResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DentistVisitService {

    private final DentistVisitDao dentistVisitDao;

    public void addVisit(DentistVisitDTO dentistVisitDTO) {
        DentistVisitEntity visit = new DentistVisitEntity(dentistVisitDTO.getDentistName(),
                dentistVisitDTO.getPhysicianName(),
                Timestamp.valueOf(dentistVisitDTO.getVisitBeginningDateTime()),
                Timestamp.valueOf(dentistVisitDTO.getVisitEndDateTime()));
        dentistVisitDao.create(visit);
    }

    public List<DentistVisitDTO> listVisits() {
        return dentistVisitDao.getAllVisits().stream()
                .map(e -> new DentistVisitDTO(
                        e.getDentistName(),
                        e.getPhysicianName(),
                        e.getVisitBeginningDateTime().toLocalDateTime(),
                        e.getVisitEndDateTime().toLocalDateTime()
                ))
                .collect(Collectors.toList());
    }

    public List<SearchQueryResultDTO> getSearchResults(SearchQueryDTO searchQueryDTO) {
        return dentistVisitDao.getSearchResults(searchQueryDTO).stream()
                .map(e -> new SearchQueryResultDTO(
                        e.getId(),
                        e.getDentistName(),
                        e.getPhysicianName(),
                        e.getVisitBeginningDateTime().toLocalDateTime(),
                        e.getVisitEndDateTime().toLocalDateTime()
                ))
                .collect(Collectors.toList());
    }

    public DetailedViewDTO getVisitByID(Long ID) {
        DentistVisitEntity entity = dentistVisitDao.getByID(ID);
        return new DetailedViewDTO(entity.getId(), entity.getDentistName(), entity.getPhysicianName(),
                entity.getVisitBeginningDateTime().toLocalDateTime(), entity.getVisitEndDateTime().toLocalDateTime());
    }

    public DetailedViewDTO setVisitByID(DetailedViewDTO dto) {
        dentistVisitDao.setByID(dto);
        return dto;
    }

    public void deleteByID(Long id) {
        dentistVisitDao.deleteByID(id);
    }

    public long getOverlapCount(DentistVisitDTO dto) {
        return dentistVisitDao.countOverlaps(Timestamp.valueOf(dto.getVisitBeginningDateTime()),
                Timestamp.valueOf(dto.getVisitEndDateTime()),
                dto.getDentistName());
    }


}
