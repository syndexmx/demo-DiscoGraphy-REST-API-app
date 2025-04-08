package com.github.syndexmx.demodiscography.services.impl;


import com.github.syndexmx.demodiscography.domain.Recording;
import com.github.syndexmx.demodiscography.repository.entities.RecordingEntity;
import com.github.syndexmx.demodiscography.repository.mappers.RecordingEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.RecordingRepository;
import com.github.syndexmx.demodiscography.services.RecordingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class RecordingServiceImpl implements RecordingService {

    private final RecordingRepository recordingRepository;

    @Autowired
    private RecordingServiceImpl(RecordingRepository recordingRepository, RecordingEntityMapper recordingEntityMapper) {
        this.recordingRepository = recordingRepository;
    }

    @Override
    public Recording create(Recording recording) {
        recording.setId(null);
        final RecordingEntity savedEntity = recordingRepository.save(RecordingEntityMapper
                .map(recording));
        final Recording savedRecording = RecordingEntityMapper.map(savedEntity);
        return savedRecording;
    }

    @Override
    public Recording save(Recording recording) {
        final RecordingEntity savedEntity = recordingRepository.save(RecordingEntityMapper
                .map(recording));
        final Recording savedRecording = RecordingEntityMapper.map(savedEntity);
        return savedRecording;
    }

    @Override
    public Optional<Recording> findById(Long recordingId) {
        final Optional<RecordingEntity> recordingEntityFound = recordingRepository
                .findById(recordingId);
        final Optional<Recording> recordingFound = recordingEntityFound.map(recordingEntity ->
                RecordingEntityMapper.map(recordingEntity));
        return recordingFound;
    }

    @Override
    public List<Recording> listAll() {
        final List<RecordingEntity> listOfFoundRecordingEntities = recordingRepository.findAll();
        final List<Recording> listOfFoundRecordings =listOfFoundRecordingEntities.stream()
                .map(entity -> RecordingEntityMapper.map(entity)).toList();
        return listOfFoundRecordings;
    }

    @Override
    public boolean isPresent(Long recordingId) {
        return recordingRepository.existsById(recordingId);
    }

    @Override
    public boolean isPresent(Recording recording) {
        return recordingRepository.existsById(recording.getId());
    }

    @Override
    public void deleteById(Long recordingId) {
        try {
            recordingRepository.deleteById(recordingId);
        } catch (final EmptyResultDataAccessException e) {
            log.debug("Attempted to delete non-existent recording");
        }
    }

}
