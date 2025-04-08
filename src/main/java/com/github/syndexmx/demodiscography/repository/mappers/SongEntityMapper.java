package com.github.syndexmx.demodiscography.repository.mappers;

import com.github.syndexmx.demodiscography.domain.Song;
import com.github.syndexmx.demodiscography.repository.entities.SongEntity;
import org.springframework.stereotype.Component;

@Component
public class SongEntityMapper {

    public static SongEntity map(Song song) {
        final SongEntity songEntity = SongEntity.builder()
                .id(song.getId())
                .synonym(song.getSynonym())
                .title(song.getTitle())
                .year(song.getYear())
                .authoursList(song.getAuthoursList().stream()
                        .map(authour ->
                                ArtistEntityMapper.map(authour)) // authours Mapping
                        .toList())
                .build();
        return songEntity;
    }

    public static Song map(SongEntity songEntity) {
        Song song = Song.builder()
                .id(songEntity.getId())
                .synonym(songEntity.getSynonym())
                .title(songEntity.getTitle())
                .year(songEntity.getYear())
                .authoursList(songEntity.getAuthoursList().stream()
                        .map(authour ->
                                ArtistEntityMapper.map(authour)) // authours Mapping
                        .toList())
                .build();
        return song;
    }

}
