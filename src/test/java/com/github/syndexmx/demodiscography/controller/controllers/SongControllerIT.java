package com.github.syndexmx.demodiscography.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.syndexmx.demodiscography.controller.mappers.SongDtoMapper;
import com.github.syndexmx.demodiscography.domain.Song;
import com.github.syndexmx.demodiscography.domain.SongTestSupplierKit;
import com.github.syndexmx.demodiscography.controller.dtos.SongDto;
import com.github.syndexmx.demodiscography.services.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Long;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SongControllerIT {

    private final String ROOT_API_PATH = "/api/v0/songs";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SongService songService;

    @Test
    public void testThatSongIsCreated() throws Exception {
        Song song = SongTestSupplierKit.getTestSong();
        final SongDto songDto = SongDtoMapper.map(song);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String songJson = objectMapper.writeValueAsString(songDto);
        mockMvc.perform(MockMvcRequestBuilders.post(ROOT_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(songJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        List<Song> savedSongList = songService.listAll();
        assertEquals(1, savedSongList.size());
        Song savedSong = savedSongList.get(0);
        final Long id = savedSong.getId();
        song.setId(id);
        assertEquals(song, savedSong);
    }

    @Test
    public void testThatSongIsUpdated() throws Exception {
        final Song song = SongTestSupplierKit.getTestSong();
        Song savedSong = songService.create(song);
        final Long id = savedSong.getId();
        Song modifiedSong = SongTestSupplierKit.getModifiedTestSong();
        modifiedSong.setId(id);
        final SongDto modifiedSongDto = SongDtoMapper.map(modifiedSong);
        final ObjectMapper modifiedObjectMapper = new ObjectMapper();
        final String modifiedSongJson = modifiedObjectMapper.writeValueAsString(modifiedSongDto);
        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_API_PATH + "/" + id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifiedSongJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(modifiedSongJson));
    }

    @Test
    public void testThatRetrieveReturnsNotFoundWhenAbsent() throws Exception {
        final Song song = SongTestSupplierKit.getTestNonExistentSong();
        final Long id = song.getId();
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveReturnsSongWhenExists() throws Exception {
        final Song song = SongTestSupplierKit.getTestSong();
        final Song songSaved = songService.create(song);
        final Long id = songSaved.getId();
        final SongDto songDto = SongDtoMapper.map(songSaved);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String songJson = objectMapper.writeValueAsString(songDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().json(songJson));
    }

    @Test
    public void testThatRetrieveAllReturnsEmptyWhenAbsent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatRetrieveAllReturnsListWhenExist() throws Exception {
        final Song song = SongTestSupplierKit.getTestSong();
        final Song songSaved = songService.create(song);
        final SongDto songDto = SongDtoMapper.map(songSaved);
        final List<SongDto> listSongDto = new ArrayList<>(List.of(songDto));
        final ObjectMapper objectMapper = new ObjectMapper();
        final String songListJson = objectMapper.writeValueAsString(listSongDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(songListJson));

    }

    @Test
    public void testThatDeleteSongByIdReturnsHttp204WhenAbsent() throws Exception {
        final Song song = SongTestSupplierKit.getTestSong();
        final Long id = song.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteSongByIdDeletesSong() throws Exception {
        final Song song = SongTestSupplierKit.getTestSong();
        final Song savedSong = songService.save(song);
        final Long id = savedSong.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
