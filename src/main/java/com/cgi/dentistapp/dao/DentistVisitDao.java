package com.cgi.dentistapp.dao;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DetailedViewDTO;
import com.cgi.dentistapp.dto.SearchQueryDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
        return entityManager.createQuery("SELECT e FROM DentistVisitEntity e", DentistVisitEntity.class)
                .getResultList();
    }

    public List<DentistVisitEntity> getSearchResults(SearchQueryDTO searchQueryDTO) {
        return entityManager.createQuery(
                "SELECT e FROM DentistVisitEntity e " +
                        " WHERE e.dentistName LIKE :dentistName " +
                        " AND e.physicianName LIKE :physicianName " +
                        " AND (:datetime IS NULL OR e.visitDateTime = :datetime)",
                DentistVisitEntity.class)
                .setParameter("dentistName", "%" + searchQueryDTO.getDentistName() + "%")
                .setParameter("physicianName", "%" + searchQueryDTO.getPhysicianName() + "%")
                .setParameter("datetime", searchQueryDTO.getVisitDateTime() == null ?
                        null : Timestamp.valueOf(searchQueryDTO.getVisitDateTime()))
                .getResultList();

    }

    public DentistVisitEntity getByID(Long id) {
        return (DentistVisitEntity) entityManager.createQuery("SELECT e FROM DentistVisitEntity e " +
                "WHERE e.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    public void setByID(DetailedViewDTO dto) {
        entityManager.merge(
                new DentistVisitEntity(dto.getId(),
                        dto.getDentistName(),
                        dto.getPhysicianName(),
                        Timestamp.valueOf(dto.getVisitDateTime())));
    }

    public void deleteByID(Long id) {
        entityManager.remove(entityManager.find(DentistVisitEntity.class, id));
    }
}
