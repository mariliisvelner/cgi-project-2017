package com.cgi.dentistapp.dao;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.SearchQueryDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class DentistVisitDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(DentistVisitEntity visit) {
        entityManager.persist(visit);
    }

    public List<DentistVisitEntity> getAllVisits() {
        return entityManager.createQuery("SELECT e FROM DentistVisitEntity e").getResultList();
    }

    public List<DentistVisitEntity> getSearchResults(SearchQueryDTO searchQueryDTO) {
        if (searchQueryDTO.getVisitDateTime() == null) {
            return entityManager.createQuery("SELECT e FROM DentistVisitEntity e " +
                    "WHERE e.dentistName LIKE :dentistName AND e.physicianName LIKE :physicianName")
                    .setParameter("dentistName", "%" + searchQueryDTO.getDentistName() + "%")
                    .setParameter("physicianName", "%" + searchQueryDTO.getPhysicianName() + "%")
                    .getResultList();
        }
        else {
            return entityManager.createQuery("SELECT e FROM DentistVisitEntity e " +
                    "WHERE e.dentistName LIKE :dentistName AND e.physicianName LIKE :physicianName AND e.visitDateTime = :datetime")
                    .setParameter("dentistName", "%" + searchQueryDTO.getDentistName() + "%")
                    .setParameter("physicianName", "%" + searchQueryDTO.getPhysicianName() + "%")
                    .setParameter("datetime", Timestamp.valueOf(searchQueryDTO.getVisitDateTime()))
                    .getResultList();
        }
    }

    public DentistVisitEntity getByID(Long id){
        return (DentistVisitEntity) entityManager.createQuery("SELECT e FROM DentistVisitEntity e " +
                "WHERE e.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}
