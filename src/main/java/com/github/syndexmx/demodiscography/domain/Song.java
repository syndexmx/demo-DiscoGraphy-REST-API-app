package com.github.syndexmx.demodiscography.domain;

import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Song {

    private Long id;
    private String synonym;
    private String title;
    private Integer year;
    private List<Artist> authoursList;

}
