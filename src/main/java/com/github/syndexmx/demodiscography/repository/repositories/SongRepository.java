package com.github.syndexmx.demodiscography.repository.repositories;

import com.github.syndexmx.demodiscography.repository.entities.GroupEntity;
import com.github.syndexmx.demodiscography.repository.entities.SongEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "song_entity-graph")
    List<SongEntity> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "song_entity-graph")
    Optional<SongEntity> findById(Long id);

}
