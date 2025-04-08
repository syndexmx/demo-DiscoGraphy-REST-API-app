package com.github.syndexmx.demodiscography.controller.mappers;

import com.github.syndexmx.demodiscography.controller.dtos.ArtistDto;
import com.github.syndexmx.demodiscography.domain.enums.Sex;
import com.github.syndexmx.demodiscography.domain.Artist;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ArtistDtoMapper {

    public static ArtistDto map(Artist artist) {
        final ArtistDto artistDto = ArtistDto.builder()
                .id(artist.getId())
                .secondName(artist.getSecondName())
                .birthDate(artist.getBirthDate().toString())
                .firstName(artist.getFirstName())
                .name(artist.getName())
                .birthPlace(artist.getBirthPlace())
                .lastName(artist.getLastName())
                .sex(artist.getSex().toString())
                .build();
        return artistDto;
    }

    public static Artist map(ArtistDto artistDto) {
        Artist artist = Artist.builder()
                .id(artistDto.getId())
                .secondName(artistDto.getSecondName())
                .birthDate(LocalDate.parse(artistDto.getBirthDate()))
                .firstName(artistDto.getFirstName())
                .name(artistDto.getName())
                .birthPlace(artistDto.getBirthPlace())
                .lastName(artistDto.getLastName())
                .sex(Sex.valueOf(artistDto.getSex()))
                .build();
        return artist;
    }

    public static Artist mapWithNoId(ArtistDto artistDto) {
        Artist artist = Artist.builder()
                .id(null)
                .secondName(artistDto.getSecondName())
                .birthDate(LocalDate.parse(artistDto.getBirthDate()))
                .firstName(artistDto.getFirstName())
                .name(artistDto.getName())
                .birthPlace(artistDto.getBirthPlace())
                .lastName(artistDto.getLastName())
                .sex(Sex.valueOf(artistDto.getSex()))
                .build();
        return artist;
    }

}
