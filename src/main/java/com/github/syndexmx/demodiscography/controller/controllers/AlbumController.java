package com.github.syndexmx.demodiscography.controller.controllers;

import com.github.syndexmx.demodiscography.controller.mappers.AlbumDtoMapper;
import com.github.syndexmx.demodiscography.domain.Album;
import com.github.syndexmx.demodiscography.controller.dtos.AlbumDto;
import com.github.syndexmx.demodiscography.services.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class AlbumController {

    private final String ROOT_API_PATH = "/api/v0/albums";

    private final AlbumService albumService;

    @Autowired
    private AlbumController(AlbumService albumService, AlbumDtoMapper albumDtoMapper) {
        this.albumService = albumService;
    }

    @PostMapping(ROOT_API_PATH)
    public ResponseEntity<AlbumDto> create(@RequestBody final AlbumDto albumDto) {
        log.info("PUT @ " + ROOT_API_PATH + " : " + albumDto.toString());
        final Album album = AlbumDtoMapper.mapWithNoId(albumDto);
        final ResponseEntity<AlbumDto> responseEntity = new ResponseEntity<> (
                AlbumDtoMapper.map(albumService.create(album)), HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping(ROOT_API_PATH +"/{albumId}")
    public ResponseEntity<AlbumDto> retrieve(@PathVariable Long albumId) {
        log.debug("GET @ " + ROOT_API_PATH + " : " + albumId.toString());
        final Optional<Album> foundAlbum = albumService.findById(albumId);
        if (foundAlbum.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final AlbumDto albumDto = AlbumDtoMapper.map(foundAlbum.get());
            return new ResponseEntity<>(albumDto, HttpStatus.FOUND);
        }
    }

    @GetMapping(ROOT_API_PATH)
    public ResponseEntity<List<AlbumDto>> retrieveAll() {
        log.debug("GET @ " + ROOT_API_PATH);
        final List<Album> listFound = albumService.listAll();
        final List<AlbumDto> listFoundDtos = listFound.stream()
                .map(album -> AlbumDtoMapper.map(album)).toList();
        final ResponseEntity<List<AlbumDto>> response = new ResponseEntity<>(listFoundDtos,
                HttpStatus.OK);
        return response;
    }

    @PutMapping(ROOT_API_PATH +"/{albumId}")
    public ResponseEntity<AlbumDto> update(@PathVariable final String albumId,
                                             @RequestBody final AlbumDto albumDto) {
        log.info("PUT @ " + ROOT_API_PATH + "/" + albumId + " : " + albumDto.toString());
        final Album album = AlbumDtoMapper.map(albumDto);
        if (!albumService.isPresent(album)) {
            final ResponseEntity<AlbumDto> responseEntity = new ResponseEntity<> (
                    AlbumDtoMapper.map(albumService.save(album)), HttpStatus.CREATED);
            return responseEntity;
        }
        final ResponseEntity<AlbumDto> responseEntity = new ResponseEntity<> (
                AlbumDtoMapper.map(albumService.save(album)), HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping(ROOT_API_PATH +"/{albumId}")
    public ResponseEntity deleteById(@PathVariable Long albumId) {
        log.info("DELETE @ " + ROOT_API_PATH + " : " + albumId.toString());
        albumService.deleteById(albumId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
