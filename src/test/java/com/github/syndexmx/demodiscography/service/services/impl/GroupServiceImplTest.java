package com.github.syndexmx.demodiscography.service.services.impl;


import com.github.syndexmx.demodiscography.domain.Group;
import com.github.syndexmx.demodiscography.domain.GroupTestSupplierKit;
import com.github.syndexmx.demodiscography.repository.entities.GroupEntity;
import com.github.syndexmx.demodiscography.repository.mappers.GroupEntityMapper;
import com.github.syndexmx.demodiscography.repository.repositories.GroupRepository;
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
public class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl underTest;

    @Test
    public void testThatGroupIsCreated() {
        Group group = GroupTestSupplierKit.getTestGroup();
        GroupEntity groupEntity = GroupEntityMapper.map(group);
        when(groupRepository.save(any())).thenReturn(groupEntity);
        final Group savedGroup = underTest.create(group);
        group.setId(savedGroup.getId());
        assertEquals(group, savedGroup);
    }

    @Test
    public void testThatGroupIsSaved() {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final GroupEntity groupEntity = GroupEntityMapper.map(group);
        when(groupRepository.save(eq(groupEntity))).thenReturn(groupEntity);
        final Group savedGroup = underTest.save(group);
        assertEquals(group, savedGroup);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoEntity() {
        final Group nonExistentGroup = GroupTestSupplierKit.getTestNonExistentGroup();
        final Long nonExistentId = nonExistentGroup.getId();
        when(groupRepository.findById(eq(nonExistentId))).thenReturn(Optional.empty());
        final Optional<Group> foundGroup = underTest.findById(nonExistentId);
        assertEquals(Optional.empty(), foundGroup);
    }

    @Test
    public void testThatFindByIdReturnsEntityWhenPresent() {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final GroupEntity groupEntity = GroupEntityMapper.map(group);
        final Long idString = group.getId();
        when(groupRepository.findById(eq(idString))).thenReturn(Optional.of(groupEntity));
        final Optional<Group> foundGroup = underTest.findById(idString);
        assertEquals(Optional.of(group), foundGroup);
    }

    @Test
    public void testListGroupsReturnsEmptyListWhenAbsent() {
        when(groupRepository.findAll()).thenReturn(new ArrayList<GroupEntity>());
        final List<Group> result = underTest.listAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testListGroupsReturnsListWhenExist() {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final GroupEntity groupEntity = GroupEntityMapper.map(group);
        List<GroupEntity> listOfExisting = new ArrayList<>(List.of(groupEntity));
        when(groupRepository.findAll()).thenReturn(listOfExisting);
        final List<Group> result = underTest.listAll();
        assertEquals(listOfExisting.size(), result.size());
    }

    @Test
    public void testThatIsPresentReturnsFalseWhenAbsent() {
        when(groupRepository.existsById(any())).thenReturn(false);
        final Group nonExistentGroup = GroupTestSupplierKit.getTestNonExistentGroup();
        final Long nonExistentUuid = nonExistentGroup.getId();
        boolean result = underTest.isPresent(nonExistentUuid);
        assertFalse(result);
    }

    @Test
    public void testThatIsPresentReturnsTrueWhenExists() {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final Long idString = group.getId();
        when(groupRepository.existsById(idString)).thenReturn(true);
        boolean result = underTest.isPresent(idString);
        assertTrue(result);
    }

    @Test
    public void testThatGroupIsPresentReturnsFalseWhenAbsent() {
        when(groupRepository.existsById(any())).thenReturn(false);
        final Group nonExistentGroup = GroupTestSupplierKit.getTestNonExistentGroup();
        boolean result = underTest.isPresent(nonExistentGroup);
        assertFalse(result);
    }

    @Test
    public void testThatGroupIsPresentReturnsTrueWhenExists() {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final String idString = group.getId().toString();
        when(groupRepository.existsById(Long.fromString(idString))).thenReturn(true);
        boolean result = underTest.isPresent(group);
        assertTrue(result);
    }

    @Test
    public void testThatDeleteGroupDeletesGroup() {
        final Group group = GroupTestSupplierKit.getTestGroup();
        final Long idString = group.getId();
        underTest.deleteById(idString);
        verify(groupRepository).deleteById(eq(idString));
    }
}
