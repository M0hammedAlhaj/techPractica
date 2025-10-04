package com.spring.techpractica.infrastructure.Jpa.Technology;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaTechnology extends JpaRepository<Technology, UUID> {
    boolean existsByName(String name);

    List<Technology> findAllByNameIn(Collection<String> names);

    Optional<Technology> findTechnologyByName(String technology);

    @Query("""
       select t
       from Technology t
       join t.fields f
       where f.id = :fieldId
       """)
    List<Technology> findAllByFieldId(@Param("fieldId") UUID fieldId);


    void deleteById(UUID id);
}
