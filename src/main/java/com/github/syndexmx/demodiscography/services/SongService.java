package com.github.syndexmx.demodiscography.services;

import com.github.syndexmx.demodiscography.domain.Song;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SongService {

    Song create(Song song);

    Song save(Song song);

    Optional<Song> findById(Long songId);

    List<Song> listAll();

    boolean isPresent(Long songId);

    boolean isPresent(Song song);

    void deleteById(Long songId);

}
