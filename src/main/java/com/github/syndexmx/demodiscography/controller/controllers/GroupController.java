package com.github.syndexmx.demodiscography.controller.controllers;


import com.github.syndexmx.demodiscography.controller.mappers.GroupDtoMapper;
import com.github.syndexmx.demodiscography.domain.Group;
import com.github.syndexmx.demodiscography.controller.dtos.GroupDto;
import com.github.syndexmx.demodiscography.services.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
public class GroupController {

    private final String ROOT_API_PATH = "/api/v0/groups";

    private final GroupService groupService;

    @Autowired
    private GroupController(GroupService groupService, GroupDtoMapper groupDtoMapper) {
        this.groupService = groupService;
    }

    @PostMapping(ROOT_API_PATH)
    public ResponseEntity<GroupDto> create(@RequestBody final GroupDto groupDto) {
        log.info("PUT @ " + ROOT_API_PATH + " : " + groupDto.toString());
        final Group group = GroupDtoMapper.mapWithNoId(groupDto);
        final ResponseEntity<GroupDto> responseEntity = new ResponseEntity<> (
                GroupDtoMapper.map(groupService.create(group)), HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping(ROOT_API_PATH +"/{groupId}")
    public ResponseEntity<GroupDto> retrieve(@PathVariable Long groupId) {
        log.debug("GET @ " + ROOT_API_PATH + " : " + groupId.toString());
        final Optional<Group> foundGroup = groupService.findById(groupId);
        if (foundGroup.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final GroupDto groupDto = GroupDtoMapper.map(foundGroup.get());
            return new ResponseEntity<>(groupDto, HttpStatus.FOUND);
        }
    }

    @GetMapping(ROOT_API_PATH)
    public ResponseEntity<List<GroupDto>> retrieveAll() {
        log.debug("GET @ " + ROOT_API_PATH);
        final List<Group> listFound = groupService.listAll();
        final List<GroupDto> listFoundDtos = listFound.stream()
                .map(group -> GroupDtoMapper.map(group)).toList();
        final ResponseEntity<List<GroupDto>> response = new ResponseEntity<>(listFoundDtos,
                HttpStatus.OK);
        return response;
    }

    @PutMapping(ROOT_API_PATH +"/{groupId}")
    public ResponseEntity<GroupDto> update(@PathVariable final String groupId,
                                             @RequestBody final GroupDto groupDto) {
        log.info("PUT @ " + ROOT_API_PATH + "/" + groupId + " : " + groupDto.toString());
        final Group group = GroupDtoMapper.map(groupDto);
        if (!groupService.isPresent(group)) {
            final ResponseEntity<GroupDto> responseEntity = new ResponseEntity<> (
                    GroupDtoMapper.map(groupService.save(group)), HttpStatus.CREATED);
            return responseEntity;
        }
        final ResponseEntity<GroupDto> responseEntity = new ResponseEntity<> (
                GroupDtoMapper.map(groupService.save(group)), HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping(ROOT_API_PATH +"/{groupId}")
    public ResponseEntity deleteById(@PathVariable Long groupId) {
        log.info("DELETE @ " + ROOT_API_PATH + " : " + groupId.toString());
        groupService.deleteById(groupId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
