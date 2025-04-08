package com.github.syndexmx.demodiscography.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.syndexmx.demodiscography.controller.mappers.ArtistDtoMapper;
import com.github.syndexmx.demodiscography.domain.Artist;
import com.github.syndexmx.demodiscography.domain.ArtistTestSupplierKit;
import com.github.syndexmx.demodiscography.controller.dtos.ArtistDto;
import com.github.syndexmx.demodiscography.services.ArtistService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ArtistControllerIT {

    private final String ROOT_API_PATH = "/api/v0/artists";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistService artistService;

    @Test
    public void testThatArtistIsCreated() throws Exception {
        Artist artist = ArtistTestSupplierKit.getTestArtist();
        final ArtistDto artistDto = ArtistDtoMapper.map(artist);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String artistJson = objectMapper.writeValueAsString(artistDto);
        mockMvc.perform(MockMvcRequestBuilders.post(ROOT_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(artistJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        List<Artist> savedArtistList = artistService.listAll();
        assertEquals(1, savedArtistList.size());
        Artist savedArtist = savedArtistList.get(0);
        final Long id = savedArtist.getId();
        artist.setId(id);
        assertEquals(artist, savedArtist);
    }

    @Test
    public void testThatArtistIsUpdated() throws Exception {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        Artist savedArtist = artistService.create(artist);
        final Long id = savedArtist.getId();
        Artist modifiedArtist = ArtistTestSupplierKit.getModifiedTestArtist();
        modifiedArtist.setId(id);
        final ArtistDto modifiedArtistDto = ArtistDtoMapper.map(modifiedArtist);
        final ObjectMapper modifiedObjectMapper = new ObjectMapper();
        final String modifiedArtistJson = modifiedObjectMapper.writeValueAsString(modifiedArtistDto);
        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_API_PATH + "/" + id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifiedArtistJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(modifiedArtistJson));
    }

    @Test
    public void testThatRetrieveReturnsNotFoundWhenAbsent() throws Exception {
        final Artist artist = ArtistTestSupplierKit.getTestNonExistentArtist();
        final Long id = artist.getId();
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveReturnsArtistWhenExists() throws Exception {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final Artist artistSaved = artistService.create(artist);
        final Long id = artistSaved.getId();
        final ArtistDto artistDto = ArtistDtoMapper.map(artistSaved);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String artistJson = objectMapper.writeValueAsString(artistDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().json(artistJson));
    }

    @Test
    public void testThatRetrieveAllReturnsEmptyWhenAbsent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatRetrieveAllReturnsListWhenExist() throws Exception {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final Artist artistSaved = artistService.create(artist);
        final ArtistDto artistDto = ArtistDtoMapper.map(artistSaved);
        final List<ArtistDto> listArtistDto = new ArrayList<>(List.of(artistDto));
        final ObjectMapper objectMapper = new ObjectMapper();
        final String artistListJson = objectMapper.writeValueAsString(listArtistDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(artistListJson));

    }

    @Test
    public void testThatDeleteArtistByIdReturnsHttp204WhenAbsent() throws Exception {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final Long id = artist.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteArtistByIdDeletesArtist() throws Exception {
        final Artist artist = ArtistTestSupplierKit.getTestArtist();
        final Artist savedArtist = artistService.save(artist);
        final Long id = savedArtist.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
