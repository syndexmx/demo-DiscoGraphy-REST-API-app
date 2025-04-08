package com.github.syndexmx.demodiscography.services;

import com.github.syndexmx.demodiscography.domain.Group;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GroupService {

    Group create(Group group);

    Group save(Group group);

    Optional<Group> findById(Long groupId);

    List<Group> listAll();

    boolean isPresent(Long groupId);

    boolean isPresent(Group group);

    void deleteById(Long groupId);

}
