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
public class AlbumDto {

    private Long id;
    private String title;
    private String place;
    private Integer year;
    private Integer length;
    private String studio;
    private List<RecordingDto> recordingList;
    private List<ArtistDto> artistList;
    private List<GroupDto> groupList;

}
