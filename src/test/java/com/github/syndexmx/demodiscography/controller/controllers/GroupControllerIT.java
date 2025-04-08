package com.github.syndexmx.demodiscography.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.syndexmx.demodiscography.controller.mappers.GroupDtoMapper;
import com.github.syndexmx.demodiscography.domain.Group;
import com.github.syndexmx.demodiscography.domain.GroupTestSupplierKit;
import com.github.syndexmx.demodiscography.controller.dtos.GroupDto;
import com.github.syndexmx.demodiscography.services.GroupService;
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
public class GroupControllerIT {

    private final String ROOT_API_PATH = "/api/v0/groups";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupService groupService;

    @Test
    public void testThatGroupIsCreated() throws Exception {
        Group group = GroupTestSupplierKit.getTestGroup();
        final GroupDto groupDto = GroupDtoMapper.map(group);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String groupJson = objectMapper.writeValueAsString(groupDto);
        mockMvc.perform(MockMvcRequestBuilders.post(ROOT_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(groupJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        List<Group> savedGroupList = groupService.listAll();
        assertEquals(1, savedGroupList.size());
        Group savedGroup = savedGroupList.get(0);
        final Long id = savedGroup.getId();
        group.setId(id);
        assertEquals(group, savedGroup);
    }

    @Test
    public void testThatGroupIsUpdated() throws Exception {
        final Group group = GroupTestSupplierKit.getTestGroup();
        Group savedGroup = groupService.create(group);
        final Long id = savedGroup.getId();
        Group modifiedGroup = GroupTestSupplierKit.getModifiedTestGroup();
        modifiedGroup.setId(id);
        final GroupDto modifiedGroupDto = GroupDtoMapper.map(modifiedGroup);
        final ObjectMapper modifiedObjectMapper = new ObjectMapper();
        final String modifiedGroupJson = modifiedObjectMapper.writeValueAsString(modifiedGroupDto);
        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_API_PATH + "/" + id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifiedGroupJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(modifiedGroupJson));
    }

    @Test
    public void testThatRetrieveReturnsNotFoundWhenAbsent() throws Exception {
        final Group group = GroupTestSupplierKit.getTestNonExistentGroup();
        final Long id = group.getId();
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveReturnsGroupWhenExists() throws Exception {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final Group groupSaved = groupService.create(group);
        final Long id = groupSaved.getId();
        final GroupDto groupDto = GroupDtoMapper.map(groupSaved);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String groupJson = objectMapper.writeValueAsString(groupDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().json(groupJson));
    }

    @Test
    public void testThatRetrieveAllReturnsEmptyWhenAbsent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatRetrieveAllReturnsListWhenExist() throws Exception {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final Group groupSaved = groupService.create(group);
        final GroupDto groupDto = GroupDtoMapper.map(groupSaved);
        final List<GroupDto> listGroupDto = new ArrayList<>(List.of(groupDto));
        final ObjectMapper objectMapper = new ObjectMapper();
        final String groupListJson = objectMapper.writeValueAsString(listGroupDto);
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(groupListJson));

    }

    @Test
    public void testThatDeleteGroupByIdReturnsHttp204WhenAbsent() throws Exception {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final Long id = group.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteGroupByIdDeletesGroup() throws Exception {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final Group savedGroup = groupService.save(group);
        final Long id = savedGroup.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_API_PATH + "/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
