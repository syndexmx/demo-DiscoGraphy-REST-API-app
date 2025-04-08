package com.github.syndexmx.demodiscography.services;

import com.github.syndexmx.demodiscography.domain.Recording;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RecordingService {

    Recording create(Recording recording);

    Recording save(Recording recording);

    Optional<Recording> findById(Long recordingId);

    List<Recording> listAll();

    boolean isPresent(Long recordingId);

    boolean isPresent(Recording recording);

    void deleteById(Long recordingId);

}
