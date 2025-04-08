package com.github.syndexmx.demodiscography.service.services.impl;


import com.github.syndexmx.demodiscography.domain.Song;
import com.github.syndexmx.demodiscography.domain.SongTestSupplierKit;
import com.github.syndexmx.demodiscography.repository.entities.SongEntity;
import com.github.syndexmx.demodiscography.repository.mappers.SongEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.SongRepository;
import com.github.syndexmx.demodiscography.services.impl.SongServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongServiceImpl underTest;

    @Test
    public void testThatSongIsCreated() {
        Song song = SongTestSupplierKit.getTestSong();
        SongEntity songEntity = SongEntityMapper.map(song);
        when(songRepository.save(any())).thenReturn(songEntity);
        final Song savedSong = underTest.create(song);
        song.setId(savedSong.getId());
        assertEquals(song, savedSong);
    }

    @Test
    public void testThatSongIsSaved() {
        final Song song = SongTestSupplierKit.getTestSong();
        final SongEntity songEntity = SongEntityMapper.map(song);
        when(songRepository.save(eq(songEntity))).thenReturn(songEntity);
        final Song savedSong = underTest.save(song);
        assertEquals(song, savedSong);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoEntity() {
        final Song nonExistentSong = SongTestSupplierKit.getTestNonExistentSong();
        final Long nonExistentId = nonExistentSong.getId();
        when(songRepository.findById(eq(nonExistentId))).thenReturn(Optional.empty());
        final Optional<Song> foundSong = underTest.findById(nonExistentId);
        assertEquals(Optional.empty(), foundSong);
    }

    @Test
    public void testThatFindByIdReturnsEntityWhenPresent() {
        final Song song = SongTestSupplierKit.getTestSong();
        final SongEntity songEntity = SongEntityMapper.map(song);
        final Long id = song.getId();
        when(songRepository.findById(eq(id))).thenReturn(Optional.of(songEntity));
        final Optional<Song> foundSong = underTest.findById(id);
        assertEquals(Optional.of(song), foundSong);
    }

    @Test
    public void testListSongsReturnsEmptyListWhenAbsent() {
        when(songRepository.findAll()).thenReturn(new ArrayList<SongEntity>());
        final List<Song> result = underTest.listAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testListSongsReturnsListWhenExist() {
        final Song song = SongTestSupplierKit.getTestSong();
        final SongEntity songEntity = SongEntityMapper.map(song);
        List<SongEntity> listOfExisting = new ArrayList<>(List.of(songEntity));
        when(songRepository.findAll()).thenReturn(listOfExisting);
        final List<Song> result = underTest.listAll();
        assertEquals(listOfExisting.size(), result.size());
    }

    @Test
    public void testThatIsPresentReturnsFalseWhenAbsent() {
        when(songRepository.existsById(any())).thenReturn(false);
        final Song nonExistentSong = SongTestSupplierKit.getTestNonExistentSong();
        final Long nonExistentUuid = nonExistentSong.getId();
        boolean result = underTest.isPresent(nonExistentUuid);
        assertFalse(result);
    }

    @Test
    public void testThatIsPresentReturnsTrueWhenExists() {
        final Song song = SongTestSupplierKit.getTestSong();
        final Long id = song.getId();
        when(songRepository.existsById(id)).thenReturn(true);
        boolean result = underTest.isPresent(id);
        assertTrue(result);
    }

    @Test
    public void testThatSongIsPresentReturnsFalseWhenAbsent() {
        when(songRepository.existsById(any())).thenReturn(false);
        final Song nonExistentSong = SongTestSupplierKit.getTestNonExistentSong();
        boolean result = underTest.isPresent(nonExistentSong);
        assertFalse(result);
    }

    @Test
    public void testThatSongIsPresentReturnsTrueWhenExists() {
        final Song song = SongTestSupplierKit.getTestSong();
        final Long id = song.getId();
        when(songRepository.existsById(id)).thenReturn(true);
        boolean result = underTest.isPresent(song);
        assertTrue(result);
    }

    @Test
    public void testThatDeleteSongDeletesSong() {
        final Song song = SongTestSupplierKit.getTestSong();
        final Long id = song.getId();
        underTest.deleteById(id);
        verify(songRepository).deleteById(eq(id));
    }
}
