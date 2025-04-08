package com.github.syndexmx.demodiscography.services;

import com.github.syndexmx.demodiscography.domain.Artist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ArtistService {

    Artist create(Artist artist);

    Artist save(Artist artist);

    Optional<Artist> findById(Long artistId);

    List<Artist> listAll();

    boolean isPresent(Long artistId);

    boolean isPresent(Artist artist);

    void deleteById(Long artistId);

}
