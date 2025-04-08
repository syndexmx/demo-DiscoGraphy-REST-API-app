package com.github.syndexmx.demodiscography.services.impl;


import com.github.syndexmx.demodiscography.domain.Album;
import com.github.syndexmx.demodiscography.repository.entities.AlbumEntity;
import com.github.syndexmx.demodiscography.repository.mappers.AlbumEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.AlbumRepository;
import com.github.syndexmx.demodiscography.services.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    private AlbumServiceImpl(AlbumRepository albumRepository, AlbumEntityMapper albumEntityMapper) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Album create(Album album) {
        album.setId(null);
        final AlbumEntity savedEntity = albumRepository.save(AlbumEntityMapper
                .map(album));
        final Album savedAlbum = AlbumEntityMapper.map(savedEntity);
        return savedAlbum;
    }

    @Override
    public Album save(Album album) {
        final AlbumEntity savedEntity = albumRepository.save(AlbumEntityMapper
                .map(album));
        final Album savedAlbum = AlbumEntityMapper.map(savedEntity);
        return savedAlbum;
    }

    @Override
    public Optional<Album> findById(Long albumId) {
        final Optional<AlbumEntity> albumEntityFound = albumRepository
                .findById(albumId);
        final Optional<Album> albumFound = albumEntityFound.map(albumEntity ->
                AlbumEntityMapper.map(albumEntity));
        return albumFound;
    }

    @Override
    public List<Album> listAll() {
        final List<AlbumEntity> listOfFoundAlbumEntities = albumRepository.findAll();
        final List<Album> listOfFoundAlbums =listOfFoundAlbumEntities.stream()
                .map(entity -> AlbumEntityMapper.map(entity)).toList();
        return listOfFoundAlbums;
    }

    @Override
    public boolean isPresent(Long albumId) {
        return albumRepository.existsById(albumId);
    }

    @Override
    public boolean isPresent(Album album) {
        return albumRepository.existsById(album.getId());
    }

    @Override
    public void deleteById(Long albumId) {
        try {
            albumRepository.deleteById(albumId);
        } catch (final EmptyResultDataAccessException e) {
            log.debug("Attempted to delete non-existent album");
        }
    }

}
