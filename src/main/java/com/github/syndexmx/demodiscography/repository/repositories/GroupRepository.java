package com.github.syndexmx.demodiscography.repository.repositories;

import com.github.syndexmx.demodiscography.repository.entities.GroupEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "group_entity-graph")
    List<GroupEntity> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "group_entity-graph")
    Optional<GroupEntity> findById(Long id);

}
