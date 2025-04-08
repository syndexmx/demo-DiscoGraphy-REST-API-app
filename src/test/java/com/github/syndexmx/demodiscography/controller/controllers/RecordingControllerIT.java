package com.github.syndexmx.demodiscography.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.syndexmx.demodiscography.controller.mappers.RecordingDtoMapper;
import com.github.syndexmx.demodiscography.domain.Recording;
import com.github.syndexmx.demodiscography.domain.RecordingTestSupplierKit;
import com.github.syndexmx.demodiscography.controller.dtos.RecordingDto;
import com.github.syndexmx.demodiscography.services.RecordingService;
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
public class RecordingControllerIT {

    private final String ROOT_API_PATH = "/api/v0/recordings";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecordingService recordingService;

    @Test
    public void testThatRecordingIsCreated() throws Exception {
        Recording recording = RecordingTestSupplierKit.getTestRecording();
        final RecordingDto recordingDto = RecordingDtoMapper.map(recording);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String recordingJson = objectMapper.writeValueAsString(recordingDto);
        mockMvc.perform(MockMvcRequestBuilders.post(ROOT_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(recordingJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        List<Recording> savedRecordingList = recordingService.listAll();
        assertEquals(1, savedRecordingList.size());
        Recording savedRecording = savedRecordingList.get(0);
        final Long id = savedRecording.getId();
        recording.setId(id);
        assertEquals(recording, savedRecording);
    }

    @Test
    public void testThatRecordingIsUpdated() throws Exception {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        Recording savedRecording = recordingService.create(recording);
        final Long id = savedRecording.getId();
        Recording modifiedRecording = RecordingTestSupplierKit.getModifiedTestRecording();
        modifiedRecording.setId(id);
        final RecordingDto modifiedRecordingDto = RecordingDtoMapper.map(modifiedRecording);
        final ObjectMapper modifiedObjectMapper = new ObjectMapper();
        final String modifiedRecordingJson = modifiedObjectMapper.writeValueAsString(modifiedRecordingDto);
        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_API_PATH + "/" + id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifiedRecordingJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(modifiedRecordingJson));
    }

    @Test
    public void testThatRetrieveReturnsNotFoundWhenAbsent() throws Exception {
        final Recording recording = RecordingTestSupplierKit.getTestNonExistentRecording();
        final Long id = recording.getId();
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveReturnsRecordingWhenExists() throws Exception {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final Recording recordingSaved = recordingService.create(recording);
        final Long id = recordingSaved.getId();
        final RecordingDto recordingDto = RecordingDtoMapper.map(recordingSaved);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String recordingJson = objectMapper.writeValueAsString(recordingDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().json(recordingJson));
    }

    @Test
    public void testThatRetrieveAllReturnsEmptyWhenAbsent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatRetrieveAllReturnsListWhenExist() throws Exception {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final Recording recordingSaved = recordingService.create(recording);
        final RecordingDto recordingDto = RecordingDtoMapper.map(recordingSaved);
        final List<RecordingDto> listRecordingDto = new ArrayList<>(List.of(recordingDto));
        final ObjectMapper objectMapper = new ObjectMapper();
        final String recordingListJson = objectMapper.writeValueAsString(listRecordingDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(recordingListJson));

    }

    @Test
    public void testThatDeleteRecordingByIdReturnsHttp204WhenAbsent() throws Exception {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final Long id = recording.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteRecordingByIdDeletesRecording() throws Exception {
        final Recording recording = RecordingTestSupplierKit.getTestRecording();
        final Recording savedRecording = recordingService.save(recording);
        final Long id = savedRecording.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
