package com.github.syndexmx.demodiscography.service.services.impl;


import com.github.syndexmx.demodiscography.domain.Album;
import com.github.syndexmx.demodiscography.domain.AlbumTestSupplierKit;
import com.github.syndexmx.demodiscography.repository.entities.AlbumEntity;
import com.github.syndexmx.demodiscography.repository.mappers.AlbumEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.AlbumRepository;
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
public class AlbumServiceImplTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumServiceImpl underTest;

    @Test
    public void testThatAlbumIsCreated() {
        Album album = AlbumTestSupplierKit.getTestAlbum();
        AlbumEntity albumEntity = AlbumEntityMapper.map(album);
        when(albumRepository.save(any())).thenReturn(albumEntity);
        final Album savedAlbum = underTest.create(album);
        album.setId(savedAlbum.getId());
        assertEquals(album, savedAlbum);
    }

    @Test
    public void testThatAlbumIsSaved() {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final AlbumEntity albumEntity = AlbumEntityMapper.map(album);
        when(albumRepository.save(eq(albumEntity))).thenReturn(albumEntity);
        final Album savedAlbum = underTest.save(album);
        assertEquals(album, savedAlbum);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoEntity() {
        final Album nonExistentAlbum = AlbumTestSupplierKit.getTestNonExistentAlbum();
        final Long nonExistentId = nonExistentAlbum.getId();
        when(albumRepository.findById(eq(nonExistentId))).thenReturn(Optional.empty());
        final Optional<Album> foundAlbum = underTest.findById(nonExistentId);
        assertEquals(Optional.empty(), foundAlbum);
    }

    @Test
    public void testThatFindByIdReturnsEntityWhenPresent() {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final AlbumEntity albumEntity = AlbumEntityMapper.map(album);
        final Long idString = album.getId();
        when(albumRepository.findById(eq(idString))).thenReturn(Optional.of(albumEntity));
        final Optional<Album> foundAlbum = underTest.findById(idString);
        assertEquals(Optional.of(album), foundAlbum);
    }

    @Test
    public void testListAlbumsReturnsEmptyListWhenAbsent() {
        when(albumRepository.findAll()).thenReturn(new ArrayList<AlbumEntity>());
        final List<Album> result = underTest.listAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testListAlbumsReturnsListWhenExist() {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final AlbumEntity albumEntity = AlbumEntityMapper.map(album);
        List<AlbumEntity> listOfExisting = new ArrayList<>(List.of(albumEntity));
        when(albumRepository.findAll()).thenReturn(listOfExisting);
        final List<Album> result = underTest.listAll();
        assertEquals(listOfExisting.size(), result.size());
    }

    @Test
    public void testThatIsPresentReturnsFalseWhenAbsent() {
        when(albumRepository.existsById(any())).thenReturn(false);
        final Album nonExistentAlbum = AlbumTestSupplierKit.getTestNonExistentAlbum();
        final Long nonExistentUuid = nonExistentAlbum.getId();
        boolean result = underTest.isPresent(nonExistentUuid);
        assertFalse(result);
    }

    @Test
    public void testThatIsPresentReturnsTrueWhenExists() {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final Long idString = album.getId();
        when(albumRepository.existsById(idString)).thenReturn(true);
        boolean result = underTest.isPresent(idString);
        assertTrue(result);
    }

    @Test
    public void testThatAlbumIsPresentReturnsFalseWhenAbsent() {
        when(albumRepository.existsById(any())).thenReturn(false);
        final Album nonExistentAlbum = AlbumTestSupplierKit.getTestNonExistentAlbum();
        boolean result = underTest.isPresent(nonExistentAlbum);
        assertFalse(result);
    }

    @Test
    public void testThatAlbumIsPresentReturnsTrueWhenExists() {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final String idString = album.getId().toString();
        when(albumRepository.existsById(Long.fromString(idString))).thenReturn(true);
        boolean result = underTest.isPresent(album);
        assertTrue(result);
    }

    @Test
    public void testThatDeleteAlbumDeletesAlbum() {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final Long idString = album.getId();
        underTest.deleteById(idString);
        verify(albumRepository).deleteById(eq(idString));
    }
}
