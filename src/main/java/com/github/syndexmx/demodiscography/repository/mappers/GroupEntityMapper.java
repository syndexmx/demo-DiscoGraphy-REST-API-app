package com.github.syndexmx.demodiscography.repository.mappers;

import com.github.syndexmx.demodiscography.domain.Group;
import com.github.syndexmx.demodiscography.repository.entities.GroupEntity;
import org.springframework.stereotype.Component;

@Component
public class GroupEntityMapper {

    public static GroupEntity map(Group group) {
        final GroupEntity groupEntity = GroupEntity.builder()
                .id(group.getId())
                .year(group.getYear())
                .synonym(group.getSynonym())
                .name(group.getName())
                .place(group.getPlace())
                .artistsList(group.getArtistsList().stream()
                        .map(artist ->
                                ArtistEntityMapper.map(artist)) // artists Mapping
                        .toList())
                .build();
        return groupEntity;
    }

    public static Group map(GroupEntity groupEntity) {
        Group group = Group.builder()
                .id(groupEntity.getId())
                .year(groupEntity.getYear())
                .synonym(groupEntity.getSynonym())
                .name(groupEntity.getName())
                .place(groupEntity.getPlace())
                .artistsList(groupEntity.getArtistsList().stream()
                        .map(artist ->
                                ArtistEntityMapper.map(artist)) // artists Mapping
                        .toList())
                .build();
        return group;
    }

}
