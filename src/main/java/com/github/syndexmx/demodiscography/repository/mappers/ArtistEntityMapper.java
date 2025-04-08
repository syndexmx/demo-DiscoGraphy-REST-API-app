package com.github.syndexmx.demodiscography.repository.mappers;

import com.github.syndexmx.demodiscography.domain.enums.Sex;
import com.github.syndexmx.demodiscography.domain.Artist;
import com.github.syndexmx.demodiscography.repository.entities.ArtistEntity;
import org.springframework.stereotype.Component;

@Component
public class ArtistEntityMapper {

    public static ArtistEntity map(Artist artist) {
        final ArtistEntity artistEntity = ArtistEntity.builder()
                .artistId(artist.getId())
                .secondName(artist.getSecondName())
                .birthDate(artist.getBirthDate())
                .firstName(artist.getFirstName())
                .name(artist.getName())
                .birthPlace(artist.getBirthPlace())
                .lastName(artist.getLastName())
                .sex(artist.getSex().toString())
                .build();
        return artistEntity;
    }

    public static Artist map(ArtistEntity artistEntity) {
        Artist artist = Artist.builder()
                .id(artistEntity.getArtistId())
                .secondName(artistEntity.getSecondName())
                .birthDate(artistEntity.getBirthDate())
                .firstName(artistEntity.getFirstName())
                .name(artistEntity.getName())
                .birthPlace(artistEntity.getBirthPlace())
                .lastName(artistEntity.getLastName())
                .sex(Sex.valueOf(artistEntity.getSex()))
                .build();
        return artist;
    }

}
