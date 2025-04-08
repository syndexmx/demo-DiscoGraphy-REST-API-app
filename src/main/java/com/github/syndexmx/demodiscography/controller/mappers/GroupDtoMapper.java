package com.github.syndexmx.demodiscography.controller.mappers;

import com.github.syndexmx.demodiscography.controller.dtos.GroupDto;
import com.github.syndexmx.demodiscography.domain.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupDtoMapper {

    public static GroupDto map(Group group) {
        final GroupDto groupDto = GroupDto.builder()
                .id(group.getId())
                .year(group.getYear())
                .synonym(group.getSynonym())
                .name(group.getName())
                .place(group.getPlace())
                .artistsList(group.getArtistsList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // artists Mapping
                        .toList())
                .build();
        return groupDto;
    }

    public static Group map(GroupDto groupDto) {
        Group group = Group.builder()
                .id(groupDto.getId())
                .year(groupDto.getYear())
                .synonym(groupDto.getSynonym())
                .name(groupDto.getName())
                .place(groupDto.getPlace())
                .artistsList(groupDto.getArtistsList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // artists Mapping
                        .toList())
                .build();
        return group;
    }

    public static Group mapWithNoId(GroupDto groupDto) {
        Group group = Group.builder()
                .id(null)
                .year(groupDto.getYear())
                .synonym(groupDto.getSynonym())
                .name(groupDto.getName())
                .place(groupDto.getPlace())
                .artistsList(groupDto.getArtistsList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // artists Mapping
                        .toList())
                .build();
        return group;
    }

}
