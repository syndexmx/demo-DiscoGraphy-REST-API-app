package com.github.syndexmx.demodiscography.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "albums")
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String place;
    private Integer year;
    private Integer length;
    private String studio;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<RecordingEntity> recordingList;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<ArtistEntity> artistList;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<GroupEntity> groupList;

}
