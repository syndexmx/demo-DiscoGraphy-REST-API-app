package com.github.syndexmx.demodiscography.repository.repositories;

import com.github.syndexmx.demodiscography.repository.entities.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
}
