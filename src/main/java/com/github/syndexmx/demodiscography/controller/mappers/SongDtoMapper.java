package com.github.syndexmx.demodiscography.controller.mappers;

import com.github.syndexmx.demodiscography.controller.dtos.SongDto;
import com.github.syndexmx.demodiscography.domain.Song;
import org.springframework.stereotype.Component;

@Component
public class SongDtoMapper {

    public static SongDto map(Song song) {
        final SongDto songDto = SongDto.builder()
                .id(song.getId())
                .synonym(song.getSynonym())
                .title(song.getTitle())
                .year(song.getYear())
                .authoursList(song.getAuthoursList().stream()
                        .map(authour ->
                                ArtistDtoMapper.map(authour)) // authours Mapping
                        .toList())
                .build();
        return songDto;
    }

    public static Song map(SongDto songDto) {
        Song song = Song.builder()
                .id(songDto.getId())
                .synonym(songDto.getSynonym())
                .title(songDto.getTitle())
                .year(songDto.getYear())
                .authoursList(songDto.getAuthoursList().stream()
                        .map(authour ->
                                ArtistDtoMapper.map(authour)) // authours Mapping
                        .toList())
                .build();
        return song;
    }

    public static Song mapWithNoId(SongDto songDto) {
        Song song = Song.builder()
                .id(null)
                .synonym(songDto.getSynonym())
                .title(songDto.getTitle())
                .year(songDto.getYear())
                .authoursList(songDto.getAuthoursList().stream()
                        .map(authour ->
                                ArtistDtoMapper.map(authour)) // authours Mapping
                        .toList())
                .build();
        return song;
    }

}
