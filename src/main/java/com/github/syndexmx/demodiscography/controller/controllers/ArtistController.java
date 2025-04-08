package com.github.syndexmx.demodiscography.controller.controllers;


import com.github.syndexmx.demodiscography.controller.mappers.ArtistDtoMapper;
import com.github.syndexmx.demodiscography.domain.Artist;
import com.github.syndexmx.demodiscography.controller.dtos.ArtistDto;
import com.github.syndexmx.demodiscography.services.ArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
public class ArtistController {

    private final String ROOT_API_PATH = "/api/v0/artists";

    private final ArtistService artistService;

    @Autowired
    private ArtistController(ArtistService artistService, ArtistDtoMapper artistDtoMapper) {
        this.artistService = artistService;
    }

    @PostMapping(ROOT_API_PATH)
    public ResponseEntity<ArtistDto> create(@RequestBody final ArtistDto artistDto) {
        log.info("PUT @ " + ROOT_API_PATH + " : " + artistDto.toString());
        final Artist artist = ArtistDtoMapper.mapWithNoId(artistDto);
        final ResponseEntity<ArtistDto> responseEntity = new ResponseEntity<> (
                ArtistDtoMapper.map(artistService.create(artist)), HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping(ROOT_API_PATH +"/{artistId}")
    public ResponseEntity<ArtistDto> retrieve(@PathVariable Long artistId) {
        log.debug("GET @ " + ROOT_API_PATH + " : " + artistId.toString());
        final Optional<Artist> foundArtist = artistService.findById(artistId);
        if (foundArtist.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final ArtistDto artistDto = ArtistDtoMapper.map(foundArtist.get());
            return new ResponseEntity<>(artistDto, HttpStatus.FOUND);
        }
    }

    @GetMapping(ROOT_API_PATH)
    public ResponseEntity<List<ArtistDto>> retrieveAll() {
        log.debug("GET @ " + ROOT_API_PATH);
        final List<Artist> listFound = artistService.listAll();
        final List<ArtistDto> listFoundDtos = listFound.stream()
                .map(artist -> ArtistDtoMapper.map(artist)).toList();
        final ResponseEntity<List<ArtistDto>> response = new ResponseEntity<>(listFoundDtos,
                HttpStatus.OK);
        return response;
    }

    @PutMapping(ROOT_API_PATH +"/{artistId}")
    public ResponseEntity<ArtistDto> update(@PathVariable final String artistId,
                                             @RequestBody final ArtistDto artistDto) {
        log.info("PUT @ " + ROOT_API_PATH + "/" + artistId + " : " + artistDto.toString());
        final Artist artist = ArtistDtoMapper.map(artistDto);
        if (!artistService.isPresent(artist)) {
            final ResponseEntity<ArtistDto> responseEntity = new ResponseEntity<> (
                    ArtistDtoMapper.map(artistService.save(artist)), HttpStatus.CREATED);
            return responseEntity;
        }
        final ResponseEntity<ArtistDto> responseEntity = new ResponseEntity<> (
                ArtistDtoMapper.map(artistService.save(artist)), HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping(ROOT_API_PATH +"/{artistId}")
    public ResponseEntity deleteById(@PathVariable Long artistId) {
        log.info("DELETE @ " + ROOT_API_PATH + " : " + artistId.toString());
        artistService.deleteById(artistId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
