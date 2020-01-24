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

    public List<DiagramPersistenceDto> findTechnologyOffersInPolandByPortal(String technologyName, List<String> portalNames, LocalDate dateFrom, LocalDate dateTo){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DiagramPersistenceDto> cq = cb.createQuery(DiagramPersistenceDto.class);

        Root<TechnologyOffersInPoland> root = cq.from(TechnologyOffersInPoland.class);
        Join<TechnologyOffersInPoland, City> city = root.join("city");
        Join<TechnologyOffersInPoland, Technology> technology = root.join("technology");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.between(root.get("date"), dateFrom, dateTo));
        predicates.add(cb.like(cb.lower(technology.get("name")), technologyName));

        cq.select(cb.construct(
                    DiagramPersistenceDto.class,
                    technology.get("name"),
                    root.get("date"),
                    cb.sum(root.get("pracuj"), root.get("linkedin"))
            )).where(predicates.toArray(new Predicate[]{}));

        List<DiagramPersistenceDto> results = em.createQuery(cq).getResultList();

        System.out.println(results);
        return results;
    }

}
