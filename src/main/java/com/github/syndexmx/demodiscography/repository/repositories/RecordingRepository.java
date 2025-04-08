package com.github.syndexmx.demodiscography.repository.repositories;

import com.github.syndexmx.demodiscography.repository.entities.RecordingEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordingRepository extends JpaRepository<RecordingEntity, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "recording_entity-graph")
    List<RecordingEntity> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "recording_entity-graph")
    Optional<RecordingEntity> findById(Long id);
}
