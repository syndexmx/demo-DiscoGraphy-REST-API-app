package com.github.syndexmx.demodiscography.services;

import com.github.syndexmx.demodiscography.domain.Album;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AlbumService {

    Album create(Album album);

    Album save(Album album);

    Optional<Album> findById(Long albumId);

    List<Album> listAll();

    boolean isPresent(Long albumId);

    boolean isPresent(Album album);

    void deleteById(Long albumId);

}
