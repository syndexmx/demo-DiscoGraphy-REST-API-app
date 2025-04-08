package com.github.syndexmx.demodiscography.service.services.impl;


import com.github.syndexmx.demodiscography.domain.Recording;
import com.github.syndexmx.demodiscography.domain.RecordingTestSupplierKit;
import com.github.syndexmx.demodiscography.repository.entities.RecordingEntity;
import com.github.syndexmx.demodiscography.repository.mappers.RecordingEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.RecordingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Long;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RecordingServiceImplTest {

    @Mock
    private RecordingRepository recordingRepository;

    @InjectMocks
    private RecordingServiceImpl underTest;

    @Test
    public void testThatRecordingIsCreated() {
        Recording recording = RecordingTestSupplierKit.getTestRecording();
        RecordingEntity recordingEntity = RecordingEntityMapper.map(recording);
        when(recordingRepository.save(any())).thenReturn(recordingEntity);
        final Recording savedRecording = underTest.create(recording);
        recording.setId(savedRecording.getId());
        assertEquals(recording, savedRecording);
    }

    @Test
    public void testThatRecordingIsSaved() {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final RecordingEntity recordingEntity = RecordingEntityMapper.map(recording);
        when(recordingRepository.save(eq(recordingEntity))).thenReturn(recordingEntity);
        final Recording savedRecording = underTest.save(recording);
        assertEquals(recording, savedRecording);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoEntity() {
        final Recording nonExistentRecording = RecordingTestSupplierKit.getTestNonExistentRecording();
        final Long nonExistentId = nonExistentRecording.getId();
        when(recordingRepository.findById(eq(nonExistentId))).thenReturn(Optional.empty());
        final Optional<Recording> foundRecording = underTest.findById(nonExistentId);
        assertEquals(Optional.empty(), foundRecording);
    }

    @Test
    public void testThatFindByIdReturnsEntityWhenPresent() {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final RecordingEntity recordingEntity = RecordingEntityMapper.map(recording);
        final Long idString = recording.getId();
        when(recordingRepository.findById(eq(idString))).thenReturn(Optional.of(recordingEntity));
        final Optional<Recording> foundRecording = underTest.findById(idString);
        assertEquals(Optional.of(recording), foundRecording);
    }

    @Test
    public void testListRecordingsReturnsEmptyListWhenAbsent() {
        when(recordingRepository.findAll()).thenReturn(new ArrayList<RecordingEntity>());
        final List<Recording> result = underTest.listAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testListRecordingsReturnsListWhenExist() {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final RecordingEntity recordingEntity = RecordingEntityMapper.map(recording);
        List<RecordingEntity> listOfExisting = new ArrayList<>(List.of(recordingEntity));
        when(recordingRepository.findAll()).thenReturn(listOfExisting);
        final List<Recording> result = underTest.listAll();
        assertEquals(listOfExisting.size(), result.size());
    }

    @Test
    public void testThatIsPresentReturnsFalseWhenAbsent() {
        when(recordingRepository.existsById(any())).thenReturn(false);
        final Recording nonExistentRecording = RecordingTestSupplierKit.getTestNonExistentRecording();
        final Long nonExistentUuid = nonExistentRecording.getId();
        boolean result = underTest.isPresent(nonExistentUuid);
        assertFalse(result);
    }

    @Test
    public void testThatIsPresentReturnsTrueWhenExists() {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final Long idString = recording.getId();
        when(recordingRepository.existsById(idString)).thenReturn(true);
        boolean result = underTest.isPresent(idString);
        assertTrue(result);
    }

    @Test
    public void testThatRecordingIsPresentReturnsFalseWhenAbsent() {
        when(recordingRepository.existsById(any())).thenReturn(false);
        final Recording nonExistentRecording = RecordingTestSupplierKit.getTestNonExistentRecording();
        boolean result = underTest.isPresent(nonExistentRecording);
        assertFalse(result);
    }

    @Test
    public void testThatRecordingIsPresentReturnsTrueWhenExists() {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final String idString = recording.getId().toString();
        when(recordingRepository.existsById(Long.fromString(idString))).thenReturn(true);
        boolean result = underTest.isPresent(recording);
        assertTrue(result);
    }

    @Test
    public void testThatDeleteRecordingDeletesRecording() {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final Long idString = recording.getId();
        underTest.deleteById(idString);
        verify(recordingRepository).deleteById(eq(idString));
    }
}
