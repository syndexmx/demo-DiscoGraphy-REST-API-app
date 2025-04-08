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
@Table(name = "groups")
@NamedEntityGraph(name = "group_entity-graph", attributeNodes = @NamedAttributeNode("artistsList"))
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private String synonym;
    private String name;
    private String place;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<ArtistEntity> artistsList;

}
