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

    /**
     * Adds a visit to the database.
     *
     * @param registrationFormDTO an object containing the information of the visit to be added
     */
    public void addVisit(RegistrationFormDTO registrationFormDTO) {
        DentistVisitEntity visit = new DentistVisitEntity(
                registrationFormDTO.getNid(),
                registrationFormDTO.getDentistName(),
                registrationFormDTO.getPhysicianName(),
                Timestamp.valueOf(registrationFormDTO.getVisitBeginningDateTime()),
                Timestamp.valueOf(registrationFormDTO.getVisitEndDateTime()));
        dentistVisitDao.create(visit);
    }

    /**
     * @return list of DisplayVisitDTOs representing the visits currently entered in the database
     */
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

    /**
     * Returns the results of the user's query.
     *
     * @param searchQueryDTO the object containing the information of the user's input
     * @return a list of DisplayVisitDTOs representing the visits that correspond to the user's query
     */
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

    /**
     * Returns a visit by its ID.
     *
     * @param ID the ID of a visit
     * @return a DetailedViewDTO containing the information of the visit with the given ID
     */
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

    /**
     * Updates a visit.
     *
     * @param dto the object containing the information of the visit which is to be updated in the database
     * @return the dto unmodified
     */
    public DetailedViewDTO update(DetailedViewDTO dto) {
        dentistVisitDao.update(dto);
        return dto;
    }

    /**
     * Deletes a visit.
     *
     * @param id the ID of the object which is to be deleted from the database
     */
    public void deleteByID(Long id) {
        dentistVisitDao.deleteByID(id);
    }

    /**
     * Counts the number of visits in the database the given visit overlaps with.
     *
     * @param dto the object containing the visit's data
     * @return the number of visits which the given visit overlaps with
     */
    public long getOverlapCount(RegistrationFormDTO dto) {
        return dentistVisitDao.countOverlaps(
                Timestamp.valueOf(dto.getVisitBeginningDateTime()),
                Timestamp.valueOf(dto.getVisitEndDateTime()),
                dto.getDentistName(),
                dto.getNid()
        );
    }
}