package com.github.syndexmx.demodiscography.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupDto {

    private Long id;
    private Integer year;
    private String synonym;
    private String name;
    private String place;
    private List<ArtistDto> artistsList;

}
