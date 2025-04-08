package com.github.syndexmx.demodiscography.repository.repositories;

import com.github.syndexmx.demodiscography.repository.entities.AlbumEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {

    //@EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "album_entity-graph")
    // Excluded temporarily for causing exceptions "unable to fetch multiple bags"
    List<AlbumEntity> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "album_entity-graph")
    Optional<AlbumEntity> findById(Long id);
}
