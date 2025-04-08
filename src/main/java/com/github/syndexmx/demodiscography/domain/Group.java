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
public class Group {

    private Long id;
    private Integer year;
    private String synonym;
    private String name;
    private String place;
    private List<Artist> artistsList;

}
