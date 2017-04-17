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
                        " AND (:beginningDateTime IS NULL OR e.visitBeginningDateTime = :beginningDateTime)" +
                        " AND (:endDateTime IS NULL OR e.visitEndDateTime = :endDateTime)",
                DentistVisitEntity.class)
                .setParameter("dentistName", "%" + searchQueryDTO.getDentistName() + "%")
                .setParameter("physicianName", "%" + searchQueryDTO.getPhysicianName() + "%")
                .setParameter("beginningDateTime", searchQueryDTO.getVisitBeginningDateTime() == null ?
                        null : Timestamp.valueOf(searchQueryDTO.getVisitBeginningDateTime()))
                .setParameter("endDateTime", searchQueryDTO.getVisitEndDateTime() == null ?
                        null : Timestamp.valueOf(searchQueryDTO.getVisitEndDateTime()))
                .getResultList();

    }

    public DentistVisitEntity getByID(Long id) {
        return (DentistVisitEntity) entityManager.createQuery("SELECT e FROM DentistVisitEntity e " +
                "WHERE e.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    public void update(DetailedViewDTO dto) {
        entityManager.merge(
                new DentistVisitEntity(
                        dto.getId(),
                        dto.getNid(),
                        dto.getDentistName(),
                        dto.getPhysicianName(),
                        Timestamp.valueOf(dto.getVisitBeginningDateTime()),
                        Timestamp.valueOf(dto.getVisitEndDateTime())
                )
        );
    }

    public void deleteByID(Long id) {
        entityManager.remove(entityManager.find(DentistVisitEntity.class, id));
    }

    public long countOverlaps(Timestamp beginningDateTime, Timestamp endDateTime, String dentistName, String nid) {
        return entityManager.createQuery(
                "SELECT COUNT (e) FROM DentistVisitEntity e " +
                        " WHERE ((e.visitBeginningDateTime BETWEEN :beginningDateTime AND :endDateTime) " +
                        " OR (e.visitEndDateTime BETWEEN :beginningDateTime AND :endDateTime))" +
                        " AND (e.dentistName = :dentistName" +
                        " OR e.nid = :nid)", Long.class)
                .setParameter("beginningDateTime", beginningDateTime)
                .setParameter("endDateTime", endDateTime)
                .setParameter("dentistName", dentistName)
                .setParameter("nid", nid)
                .getSingleResult();
    }
}
