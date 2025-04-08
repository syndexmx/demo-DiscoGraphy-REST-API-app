package com.github.syndexmx.demodiscography.services.impl;

import com.github.syndexmx.demodiscography.domain.Song;
import com.github.syndexmx.demodiscography.repository.entities.SongEntity;
import com.github.syndexmx.demodiscography.repository.mappers.SongEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.SongRepository;
import com.github.syndexmx.demodiscography.services.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    private SongServiceImpl(SongRepository songRepository, SongEntityMapper songEntityMapper) {
        this.songRepository = songRepository;
    }

    @Override
    public Song create(Song song) {
        song.setId(null);
        final SongEntity savedEntity = songRepository.save(SongEntityMapper
                .map(song));
        final Song savedSong = SongEntityMapper.map(savedEntity);
        return savedSong;
    }

    @Override
    public Song save(Song song) {
        final SongEntity savedEntity = songRepository.save(SongEntityMapper
                .map(song));
        final Song savedSong = SongEntityMapper.map(savedEntity);
        return savedSong;
    }

    @Override
    public Optional<Song> findById(Long songId) {
        final Optional<SongEntity> songEntityFound = songRepository
                .findById(songId);
        final Optional<Song> songFound = songEntityFound.map(songEntity ->
                SongEntityMapper.map(songEntity));
        return songFound;
    }

    @Override
    public List<Song> listAll() {
        final List<SongEntity> listOfFoundSongEntities = songRepository.findAll();
        final List<Song> listOfFoundSongs =listOfFoundSongEntities.stream()
                .map(entity -> SongEntityMapper.map(entity)).toList();
        return listOfFoundSongs;
    }

    @Override
    public boolean isPresent(Long songId) {
        return songRepository.existsById(songId);
    }

    @Override
    public boolean isPresent(Song song) {
        return songRepository.existsById(song.getId());
    }

    @Override
    public void deleteById(Long songId) {
        try {
            songRepository.deleteById(songId);
        } catch (final EmptyResultDataAccessException e) {
            log.debug("Attempted to delete non-existent song");
        }
    }

}
