package com.github.syndexmx.demodiscography.services.impl;


import com.github.syndexmx.demodiscography.domain.Artist;
import com.github.syndexmx.demodiscography.repository.entities.ArtistEntity;
import com.github.syndexmx.demodiscography.repository.mappers.ArtistEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.ArtistRepository;
import com.github.syndexmx.demodiscography.services.ArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    private ArtistServiceImpl(ArtistRepository artistRepository, ArtistEntityMapper artistEntityMapper) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Artist create(Artist artist) {
        artist.setId(null);
        final ArtistEntity savedEntity = artistRepository.save(ArtistEntityMapper
                .map(artist));
        final Artist savedArtist = ArtistEntityMapper.map(savedEntity);
        return savedArtist;
    }

    @Override
    public Artist save(Artist artist) {
        final ArtistEntity savedEntity = artistRepository.save(ArtistEntityMapper
                .map(artist));
        final Artist savedArtist = ArtistEntityMapper.map(savedEntity);
        return savedArtist;
    }

    @Override
    public Optional<Artist> findById(Long artistId) {
        final Optional<ArtistEntity> artistEntityFound = artistRepository
                .findById(artistId);
        final Optional<Artist> artistFound = artistEntityFound.map(artistEntity ->
                ArtistEntityMapper.map(artistEntity));
        return artistFound;
    }

    @Override
    public List<Artist> listAll() {
        final List<ArtistEntity> listOfFoundArtistEntities = artistRepository.findAll();
        final List<Artist> listOfFoundArtists =listOfFoundArtistEntities.stream()
                .map(entity -> ArtistEntityMapper.map(entity)).toList();
        return listOfFoundArtists;
    }

    @Override
    public boolean isPresent(Long artistId) {
        return artistRepository.existsById(artistId);
    }

    @Override
    public boolean isPresent(Artist artist) {
        return artistRepository.existsById(artist.getId());
    }

    @Override
    public void deleteById(Long artistId) {
        try {
            artistRepository.deleteById(artistId);
        } catch (final EmptyResultDataAccessException e) {
            log.debug("Attempted to delete non-existent artist");
        }
    }

}
