package com.github.syndexmx.demodiscography.services.impl;


import com.github.syndexmx.demodiscography.domain.Group;
import com.github.syndexmx.demodiscography.repository.entities.GroupEntity;
import com.github.syndexmx.demodiscography.repository.mappers.GroupEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.GroupRepository;
import com.github.syndexmx.demodiscography.services.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    private GroupServiceImpl(GroupRepository groupRepository, GroupEntityMapper groupEntityMapper) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group create(Group group) {
        group.setId(null);
        final GroupEntity savedEntity = groupRepository.save(GroupEntityMapper
                .map(group));
        final Group savedGroup = GroupEntityMapper.map(savedEntity);
        return savedGroup;
    }

    @Override
    public Group save(Group group) {
        final GroupEntity savedEntity = groupRepository.save(GroupEntityMapper
                .map(group));
        final Group savedGroup = GroupEntityMapper.map(savedEntity);
        return savedGroup;
    }

    @Override
    public Optional<Group> findById(Long groupId) {
        final Optional<GroupEntity> groupEntityFound = groupRepository
                .findById(groupId);
        final Optional<Group> groupFound = groupEntityFound.map(groupEntity ->
                GroupEntityMapper.map(groupEntity));
        return groupFound;
    }

    @Override
    public List<Group> listAll() {
        final List<GroupEntity> listOfFoundGroupEntities = groupRepository.findAll();
        final List<Group> listOfFoundGroups =listOfFoundGroupEntities.stream()
                .map(entity -> GroupEntityMapper.map(entity)).toList();
        return listOfFoundGroups;
    }

    @Override
    public boolean isPresent(Long groupId) {
        return groupRepository.existsById(groupId);
    }

    @Override
    public boolean isPresent(Group group) {
        return groupRepository.existsById(group.getId());
    }

    @Override
    public void deleteById(Long groupId) {
        try {
            groupRepository.deleteById(groupId);
        } catch (final EmptyResultDataAccessException e) {
            log.debug("Attempted to delete non-existent group");
        }
    }

}
