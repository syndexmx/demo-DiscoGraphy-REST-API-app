package com.github.syndexmx.demodiscography.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.syndexmx.demodiscography.controller.mappers.AlbumDtoMapper;
import com.github.syndexmx.demodiscography.domain.Album;
import com.github.syndexmx.demodiscography.domain.AlbumTestSupplierKit;
import com.github.syndexmx.demodiscography.controller.dtos.AlbumDto;
import com.github.syndexmx.demodiscography.services.AlbumService;
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
public class AlbumControllerIT {

    private final String ROOT_API_PATH = "/api/v0/albums";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlbumService albumService;

    @Test
    public void testThatAlbumIsCreated() throws Exception {
        Album album = AlbumTestSupplierKit.getTestAlbum();
        final AlbumDto albumDto = AlbumDtoMapper.map(album);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String albumJson = objectMapper.writeValueAsString(albumDto);
        mockMvc.perform(MockMvcRequestBuilders.post(ROOT_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(albumJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        List<Album> savedAlbumList = albumService.listAll();
        assertEquals(1, savedAlbumList.size());
        Album savedAlbum = savedAlbumList.get(0);
        final Long id = savedAlbum.getId();
        album.setId(id);
        assertEquals(album, savedAlbum);
    }

    @Test
    public void testThatAlbumIsUpdated() throws Exception {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        Album savedAlbum = albumService.create(album);
        final Long id = savedAlbum.getId();
        Album modifiedAlbum = AlbumTestSupplierKit.getModifiedTestAlbum();
        modifiedAlbum.setId(id);
        final AlbumDto modifiedAlbumDto = AlbumDtoMapper.map(modifiedAlbum);
        final ObjectMapper modifiedObjectMapper = new ObjectMapper();
        final String modifiedAlbumJson = modifiedObjectMapper.writeValueAsString(modifiedAlbumDto);
        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_API_PATH + "/" + id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifiedAlbumJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(modifiedAlbumJson));
    }

    @Test
    public void testThatRetrieveReturnsNotFoundWhenAbsent() throws Exception {
        final Album album = AlbumTestSupplierKit.getTestNonExistentAlbum();
        final Long id = album.getId();
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveReturnsAlbumWhenExists() throws Exception {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final Album albumSaved = albumService.create(album);
        final Long id = albumSaved.getId();
        final AlbumDto albumDto = AlbumDtoMapper.map(albumSaved);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String albumJson = objectMapper.writeValueAsString(albumDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().json(albumJson));
    }

    @Test
    public void testThatRetrieveAllReturnsEmptyWhenAbsent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatRetrieveAllReturnsListWhenExist() throws Exception {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final Album albumSaved = albumService.create(album);
        final AlbumDto albumDto = AlbumDtoMapper.map(albumSaved);
        final List<AlbumDto> listAlbumDto = new ArrayList<>(List.of(albumDto));
        final ObjectMapper objectMapper = new ObjectMapper();
        final String albumListJson = objectMapper.writeValueAsString(listAlbumDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(albumListJson));

    }

    @Test
    public void testThatDeleteAlbumByIdReturnsHttp204WhenAbsent() throws Exception {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final Long id = album.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAlbumByIdDeletesAlbum() throws Exception {
        final Album album = AlbumTestSupplierKit.getTestAlbum();
        final Album savedAlbum = albumService.save(album);
        final Long id = savedAlbum.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
