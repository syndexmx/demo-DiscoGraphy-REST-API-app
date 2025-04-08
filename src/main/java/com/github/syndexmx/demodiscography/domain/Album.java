package com.github.syndexmx.demodiscography.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Album {

    private Long id;
    private String title;
    private String place;
    private Integer year;
    private Integer length;
    private String studio;
    private List<Recording> recordingList;
    private List<Artist> artistList;
    private List<Group> groupList;

}
