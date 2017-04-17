package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.RegistrationFormDTO;
import com.cgi.dentistapp.dto.DetailedViewDTO;
import com.cgi.dentistapp.dto.SearchQueryDTO;
import com.cgi.dentistapp.dto.DisplayVisitDTO;
import com.cgi.dentistapp.util.DateTimeUtil;
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

    public void addVisit(RegistrationFormDTO registrationFormDTO) {
        DentistVisitEntity visit = new DentistVisitEntity(
                registrationFormDTO.getNid(),
                registrationFormDTO.getDentistName(),
                registrationFormDTO.getPhysicianName(),
                Timestamp.valueOf(registrationFormDTO.getVisitBeginningDateTime()),
                Timestamp.valueOf(registrationFormDTO.getVisitEndDateTime()));
        dentistVisitDao.create(visit);
    }

    public List<DisplayVisitDTO> listVisits() {
        return dentistVisitDao.getAllVisits().stream()
                .map(e -> new DisplayVisitDTO(
                        e.getId(),
                        e.getNid(),
                        e.getDentistName(),
                        e.getPhysicianName(),
                        DateTimeUtil.toString(e.getVisitBeginningDateTime().toLocalDateTime()),
                        DateTimeUtil.toString(e.getVisitEndDateTime().toLocalDateTime())
                ))
                .collect(Collectors.toList());
    }

    public List<DisplayVisitDTO> getSearchResults(SearchQueryDTO searchQueryDTO) {
        return dentistVisitDao.getSearchResults(searchQueryDTO).stream()
                .map(e -> new DisplayVisitDTO(
                        e.getId(),
                        e.getNid(),
                        e.getDentistName(),
                        e.getPhysicianName(),
                        DateTimeUtil.toString(e.getVisitBeginningDateTime().toLocalDateTime()),
                        DateTimeUtil.toString(e.getVisitEndDateTime().toLocalDateTime())
                ))
                .collect(Collectors.toList());
    }


    public DetailedViewDTO getVisitByID(Long ID) {
        DentistVisitEntity entity = dentistVisitDao.getByID(ID);
        return new DetailedViewDTO(
                entity.getId(),
                entity.getNid(),
                entity.getDentistName(),
                entity.getPhysicianName(),
                entity.getVisitBeginningDateTime().toLocalDateTime(),
                entity.getVisitEndDateTime().toLocalDateTime()
        );
    }

    public DetailedViewDTO update(DetailedViewDTO dto) {
        dentistVisitDao.update(dto);
        return dto;
    }

    public void deleteByID(Long id) {
        dentistVisitDao.deleteByID(id);
    }

    public long getOverlapCount(RegistrationFormDTO dto) {
        return dentistVisitDao.countOverlaps(
                Timestamp.valueOf(dto.getVisitBeginningDateTime()),
                Timestamp.valueOf(dto.getVisitEndDateTime()),
                dto.getDentistName(),
                dto.getNid()
        );
    }
}