package com.github.syndexmx.demodiscography.controller.dtos;

import com.github.syndexmx.demodiscography.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecordingDto {

    private Long id;
    private String studio;
    private Integer length;
    private Integer year;
    private String place;
    private String title;
    private SongDto song;
    private List<ArtistDto> artistList;
    private List<GroupDto> groupList;

}
