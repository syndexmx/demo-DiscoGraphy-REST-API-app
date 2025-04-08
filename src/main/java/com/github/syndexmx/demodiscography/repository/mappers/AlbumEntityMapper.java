package com.github.syndexmx.demodiscography.repository.mappers;

import com.github.syndexmx.demodiscography.domain.Album;
import com.github.syndexmx.demodiscography.repository.entities.AlbumEntity;
import org.springframework.stereotype.Component;

@Component
public class AlbumEntityMapper {

    public static AlbumEntity map(Album album) {
        final AlbumEntity albumEntity = AlbumEntity.builder()
                .id(album.getId())
                .title(album.getTitle())
                .place(album.getPlace())
                .year(album.getYear())
                .length(album.getLength())
                .studio(album.getStudio())
                .recordingList(album.getRecordingList().stream()
                        .map(recording ->
                                RecordingEntityMapper.map(recording)) // recordings Mapping
                        .toList())
                .artistList(album.getArtistList().stream()
                        .map(artist ->
                                ArtistEntityMapper.map(artist)) // recordings Mapping
                        .toList())
                .groupList(album.getGroupList().stream()
                        .map(group ->
                                GroupEntityMapper.map(group)) // recordings Mapping
                        .toList())
                .build();
        return albumEntity;
    }

    public static Album map(AlbumEntity albumEntity) {
        Album album = Album.builder()
                .id(albumEntity.getId())
                .title(albumEntity.getTitle())
                .place(albumEntity.getPlace())
                .year(albumEntity.getYear())
                .length(albumEntity.getLength())
                .studio(albumEntity.getStudio())
                .recordingList(albumEntity.getRecordingList().stream()
                        .map(recording ->
                                RecordingEntityMapper.map(recording)) // recordings Mapping
                        .toList())
                .artistList(albumEntity.getArtistList().stream()
                        .map(artist ->
                                ArtistEntityMapper.map(artist)) // recordings Mapping
                        .toList())
                .groupList(albumEntity.getGroupList().stream()
                        .map(group ->
                                GroupEntityMapper.map(group)) // recordings Mapping
                        .toList())
                .build();
        return album;
    }

}
