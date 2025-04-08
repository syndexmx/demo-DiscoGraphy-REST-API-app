package com.github.syndexmx.demodiscography.controller.controllers;


import com.github.syndexmx.demodiscography.controller.mappers.RecordingDtoMapper;
import com.github.syndexmx.demodiscography.domain.Recording;
import com.github.syndexmx.demodiscography.controller.dtos.RecordingDto;
import com.github.syndexmx.demodiscography.services.RecordingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
public class RecordingController {

    private final String ROOT_API_PATH = "/api/v0/recordings";

    private final RecordingService recordingService;

    @Autowired
    private RecordingController(RecordingService recordingService, RecordingDtoMapper recordingDtoMapper) {
        this.recordingService = recordingService;
    }

    @PostMapping(ROOT_API_PATH)
    public ResponseEntity<RecordingDto> create(@RequestBody final RecordingDto recordingDto) {
        log.info("PUT @ " + ROOT_API_PATH + " : " + recordingDto.toString());
        final Recording recording = RecordingDtoMapper.mapWithNoId(recordingDto);
        final ResponseEntity<RecordingDto> responseEntity = new ResponseEntity<> (
                RecordingDtoMapper.map(recordingService.create(recording)), HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping(ROOT_API_PATH +"/{recordingId}")
    public ResponseEntity<RecordingDto> retrieve(@PathVariable Long recordingId) {
        log.debug("GET @ " + ROOT_API_PATH + " : " + recordingId.toString());
        final Optional<Recording> foundRecording = recordingService.findById(recordingId);
        if (foundRecording.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final RecordingDto recordingDto = RecordingDtoMapper.map(foundRecording.get());
            return new ResponseEntity<>(recordingDto, HttpStatus.FOUND);
        }
    }

    @GetMapping(ROOT_API_PATH)
    public ResponseEntity<List<RecordingDto>> retrieveAll() {
        log.debug("GET @ " + ROOT_API_PATH);
        final List<Recording> listFound = recordingService.listAll();
        final List<RecordingDto> listFoundDtos = listFound.stream()
                .map(recording -> RecordingDtoMapper.map(recording)).toList();
        final ResponseEntity<List<RecordingDto>> response = new ResponseEntity<>(listFoundDtos,
                HttpStatus.OK);
        return response;
    }

    @PutMapping(ROOT_API_PATH +"/{recordingId}")
    public ResponseEntity<RecordingDto> update(@PathVariable final String recordingId,
                                             @RequestBody final RecordingDto recordingDto) {
        log.info("PUT @ " + ROOT_API_PATH + "/" + recordingId + " : " + recordingDto.toString());
        final Recording recording = RecordingDtoMapper.map(recordingDto);
        if (!recordingService.isPresent(recording)) {
            final ResponseEntity<RecordingDto> responseEntity = new ResponseEntity<> (
                    RecordingDtoMapper.map(recordingService.save(recording)), HttpStatus.CREATED);
            return responseEntity;
        }
        final ResponseEntity<RecordingDto> responseEntity = new ResponseEntity<> (
                RecordingDtoMapper.map(recordingService.save(recording)), HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping(ROOT_API_PATH +"/{recordingId}")
    public ResponseEntity deleteById(@PathVariable Long recordingId) {
        log.info("DELETE @ " + ROOT_API_PATH + " : " + recordingId.toString());
        recordingService.deleteById(recordingId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
