package com.Backend.infrastructure.repository.implementation;

import com.Backend.infrastructure.dto.DiagramPersistenceDto;
import com.Backend.infrastructure.entity.City;
import com.Backend.infrastructure.entity.Technology;
import com.Backend.infrastructure.entity.TechnologyOffersInPoland;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TechnologyOffersInPolandDao {

    @PersistenceContext
    EntityManager em;

    public List<DiagramPersistenceDto> findDiagramItJobOffersInPoland(String technologyName, LocalDate dateFrom, LocalDate dateTo){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DiagramPersistenceDto> cq = cb.createQuery(DiagramPersistenceDto.class);

        Root<TechnologyOffersInPoland> technologyOffers = cq.from(TechnologyOffersInPoland.class);
        Join<TechnologyOffersInPoland, City> city = technologyOffers.join("city");
        Join<TechnologyOffersInPoland, Technology> technology = technologyOffers.join("technology");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.between(technologyOffers.get("date"), dateFrom, dateTo));
        predicates.add(cb.like(cb.lower(technology.get("name")), technologyName));

        cq.select(cb.construct(
                DiagramPersistenceDto.class,
                city.get("name"),
                technologyOffers.get("date"),
                technologyOffers.get("linkedin"),
                technologyOffers.get("pracuj"),
                technologyOffers.get("indeed"),
                technologyOffers.get("noFluffJobs"),
                technologyOffers.get("justJoinIt")
        )).where(predicates.toArray(new Predicate[]{}));

        return em.createQuery(cq).getResultList();
    }

}
