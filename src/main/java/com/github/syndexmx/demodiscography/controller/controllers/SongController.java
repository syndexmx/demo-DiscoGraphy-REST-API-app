package com.github.syndexmx.demodiscography.controller.controllers;


import com.github.syndexmx.demodiscography.controller.mappers.SongDtoMapper;
import com.github.syndexmx.demodiscography.domain.Song;
import com.github.syndexmx.demodiscography.controller.dtos.SongDto;
import com.github.syndexmx.demodiscography.services.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
public class SongController {

    private final String ROOT_API_PATH = "/api/v0/songs";

    private final SongService songService;

    @Autowired
    private SongController(SongService songService, SongDtoMapper songDtoMapper) {
        this.songService = songService;
    }

    @PostMapping(ROOT_API_PATH)
    public ResponseEntity<SongDto> create(@RequestBody final SongDto songDto) {
        log.info("PUT @ " + ROOT_API_PATH + " : " + songDto.toString());
        final Song song = SongDtoMapper.mapWithNoId(songDto);
        final ResponseEntity<SongDto> responseEntity = new ResponseEntity<> (
                SongDtoMapper.map(songService.create(song)), HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping(ROOT_API_PATH +"/{songId}")
    public ResponseEntity<SongDto> retrieve(@PathVariable Long songId) {
        log.debug("GET @ " + ROOT_API_PATH + " : " + songId.toString());
        final Optional<Song> foundSong = songService.findById(songId);
        if (foundSong.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final SongDto songDto = SongDtoMapper.map(foundSong.get());
            return new ResponseEntity<>(songDto, HttpStatus.FOUND);
        }
    }

    @GetMapping(ROOT_API_PATH)
    public ResponseEntity<List<SongDto>> retrieveAll() {
        log.debug("GET @ " + ROOT_API_PATH);
        final List<Song> listFound = songService.listAll();
        final List<SongDto> listFoundDtos = listFound.stream()
                .map(song -> SongDtoMapper.map(song)).toList();
        final ResponseEntity<List<SongDto>> response = new ResponseEntity<>(listFoundDtos,
                HttpStatus.OK);
        return response;
    }

    @PutMapping(ROOT_API_PATH +"/{songId}")
    public ResponseEntity<SongDto> update(@PathVariable final String songId,
                                             @RequestBody final SongDto songDto) {
        log.info("PUT @ " + ROOT_API_PATH + "/" + songId + " : " + songDto.toString());
        final Song song = SongDtoMapper.map(songDto);
        if (!songService.isPresent(song)) {
            final ResponseEntity<SongDto> responseEntity = new ResponseEntity<> (
                    SongDtoMapper.map(songService.save(song)), HttpStatus.CREATED);
            return responseEntity;
        }
        final ResponseEntity<SongDto> responseEntity = new ResponseEntity<> (
                SongDtoMapper.map(songService.save(song)), HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping(ROOT_API_PATH +"/{songId}")
    public ResponseEntity deleteById(@PathVariable Long songId) {
        log.info("DELETE @ " + ROOT_API_PATH + " : " + songId.toString());
        songService.deleteById(songId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
