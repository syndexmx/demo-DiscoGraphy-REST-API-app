package com.github.syndexmx.demodiscography.controller.mappers;

import com.github.syndexmx.demodiscography.controller.dtos.AlbumDto;
import com.github.syndexmx.demodiscography.domain.Album;
import org.springframework.stereotype.Component;

@Component
public class AlbumDtoMapper {

    public static AlbumDto map(Album album) {
        final AlbumDto albumDto = AlbumDto.builder()
                .id(album.getId())
                .title(album.getTitle())
                .place(album.getPlace())
                .year(album.getYear())
                .length(album.getLength())
                .studio(album.getStudio())
                .recordingList(album.getRecordingList().stream()
                        .map(recording ->
                                RecordingDtoMapper.map(recording)) // recordings Mapping
                        .toList())
                .artistList(album.getArtistList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // recordings Mapping
                        .toList())
                .groupList(album.getGroupList().stream()
                        .map(group ->
                                GroupDtoMapper.map(group)) // recordings Mapping
                        .toList())
                .build();
        return albumDto;
    }

    public static Album map(AlbumDto albumDto) {
        Album album = Album.builder()
                .id(albumDto.getId())
                .title(albumDto.getTitle())
                .place(albumDto.getPlace())
                .year(albumDto.getYear())
                .length(albumDto.getLength())
                .studio(albumDto.getStudio())
                .recordingList(albumDto.getRecordingList().stream()
                        .map(recording ->
                                RecordingDtoMapper.map(recording)) // recordings Mapping
                        .toList())
                .artistList(albumDto.getArtistList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // recordings Mapping
                        .toList())
                .groupList(albumDto.getGroupList().stream()
                        .map(group ->
                                GroupDtoMapper.map(group)) // recordings Mapping
                        .toList())
                .build();
        return album;
    }

    public static Album mapWithNoId(AlbumDto albumDto) {
        Album album = Album.builder()
                .id(null)
                .title(albumDto.getTitle())
                .place(albumDto.getPlace())
                .year(albumDto.getYear())
                .length(albumDto.getLength())
                .studio(albumDto.getStudio())
                .recordingList(albumDto.getRecordingList().stream()
                        .map(recording ->
                                RecordingDtoMapper.map(recording)) // recordings Mapping
                        .toList())
                .artistList(albumDto.getArtistList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // recordings Mapping
                        .toList())
                .groupList(albumDto.getGroupList().stream()
                        .map(group ->
                                GroupDtoMapper.map(group)) // recordings Mapping
                        .toList())
                .build();
        return album;
    }

}
