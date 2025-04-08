package com.github.syndexmx.demodiscography.repository.entities;

import com.github.syndexmx.demodiscography.domain.Song;
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
@Table(name = "recordings")
public class RecordingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordingId;
    private String studio;
    private Integer length;
    private Integer year;
    private String place;
    private String title;

    @ManyToOne(cascade = CascadeType.DETACH)
    private SongEntity song;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<ArtistEntity> artistList;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<GroupEntity> groupList;

}
