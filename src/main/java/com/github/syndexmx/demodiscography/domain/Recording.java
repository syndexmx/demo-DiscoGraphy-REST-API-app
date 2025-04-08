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
public class Recording {

    private Long id;
    private String studio;
    private Integer length;
    private Integer year;
    private String place;
    private String title;
    private Song song;
    private List<Artist> artistList;
    private List<Group> groupList;

}
