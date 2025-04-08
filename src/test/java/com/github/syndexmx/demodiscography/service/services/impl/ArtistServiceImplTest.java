package com.github.syndexmx.demodiscography.service.services.impl;


import com.github.syndexmx.demodiscography.domain.Artist;
import com.github.syndexmx.demodiscography.domain.ArtistTestSupplierKit;
import com.github.syndexmx.demodiscography.repository.entities.ArtistEntity;
import com.github.syndexmx.demodiscography.repository.mappers.ArtistEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.ArtistRepository;
import com.github.syndexmx.demodiscography.services.impl.ArtistServiceImpl;
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
public class ArtistServiceImplTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistServiceImpl underTest;

    @Test
    public void testThatArtistIsCreated() {
        Artist artist = ArtistTestSupplierKit.getTestArtist();
        ArtistEntity artistEntity = ArtistEntityMapper.map(artist);
        when(artistRepository.save(any())).thenReturn(artistEntity);
        final Artist savedArtist = underTest.create(artist);
        artist.setId(savedArtist.getId());
        assertEquals(artist, savedArtist);
    }

    @Test
    public void testThatArtistIsSaved() {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final ArtistEntity artistEntity = ArtistEntityMapper.map(artist);
        when(artistRepository.save(eq(artistEntity))).thenReturn(artistEntity);
        final Artist savedArtist = underTest.save(artist);
        assertEquals(artist, savedArtist);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoEntity() {
        final Artist nonExistentArtist = ArtistTestSupplierKit.getTestNonExistentArtist();
        final Long nonExistentId = nonExistentArtist.getId();
        when(artistRepository.findById(eq(nonExistentId))).thenReturn(Optional.empty());
        final Optional<Artist> foundArtist = underTest.findById(nonExistentId);
        assertEquals(Optional.empty(), foundArtist);
    }

    @Test
    public void testThatFindByIdReturnsEntityWhenPresent() {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final ArtistEntity artistEntity = ArtistEntityMapper.map(artist);
        final Long id = artist.getId();
        when(artistRepository.findById(eq(id))).thenReturn(Optional.of(artistEntity));
        final Optional<Artist> foundArtist = underTest.findById(id);
        assertEquals(Optional.of(artist), foundArtist);
    }

    @Test
    public void testListArtistsReturnsEmptyListWhenAbsent() {
        when(artistRepository.findAll()).thenReturn(new ArrayList<ArtistEntity>());
        final List<Artist> result = underTest.listAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testListArtistsReturnsListWhenExist() {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final ArtistEntity artistEntity = ArtistEntityMapper.map(artist);
        List<ArtistEntity> listOfExisting = new ArrayList<>(List.of(artistEntity));
        when(artistRepository.findAll()).thenReturn(listOfExisting);
        final List<Artist> result = underTest.listAll();
        assertEquals(listOfExisting.size(), result.size());
    }

    @Test
    public void testThatIsPresentReturnsFalseWhenAbsent() {
        when(artistRepository.existsById(any())).thenReturn(false);
        final Artist nonExistentArtist = ArtistTestSupplierKit.getTestNonExistentArtist();
        final Long nonExistentUuid = nonExistentArtist.getId();
        boolean result = underTest.isPresent(nonExistentUuid);
        assertFalse(result);
    }

    @Test
    public void testThatIsPresentReturnsTrueWhenExists() {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final Long id = artist.getId();
        when(artistRepository.existsById(id)).thenReturn(true);
        boolean result = underTest.isPresent(id);
        assertTrue(result);
    }

    @Test
    public void testThatArtistIsPresentReturnsFalseWhenAbsent() {
        when(artistRepository.existsById(any())).thenReturn(false);
        final Artist nonExistentArtist = ArtistTestSupplierKit.getTestNonExistentArtist();
        boolean result = underTest.isPresent(nonExistentArtist);
        assertFalse(result);
    }

    @Test
    public void testThatArtistIsPresentReturnsTrueWhenExists() {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final Long id = artist.getId();
        when(artistRepository.existsById(id)).thenReturn(true);
        boolean result = underTest.isPresent(artist);
        assertTrue(result);
    }

    @Test
    public void testThatDeleteArtistDeletesArtist() {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final Long id = artist.getId();
        underTest.deleteById(id);
        verify(artistRepository).deleteById(eq(id));
    }
}
