package com.github.syndexmx.demodiscography.controller.mappers;

import com.github.syndexmx.demodiscography.controller.dtos.RecordingDto;
import com.github.syndexmx.demodiscography.domain.Recording;
import com.github.syndexmx.demodiscography.repository.mappers.SongEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class RecordingDtoMapper {

    public static RecordingDto map(Recording recording) {
        final RecordingDto recordingDto = RecordingDto.builder()
                .id(recording.getId())
                .studio(recording.getStudio())
                .length(recording.getLength())
                .year(recording.getYear())
                .place(recording.getPlace())
                .title(recording.getTitle())
                .song(SongDtoMapper.map(recording.getSong()))
                .artistList(recording.getArtistList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // song Mapping
                        .toList())
                .build();
        return recordingDto;
    }

    public static Recording map(RecordingDto recordingDto) {
        Recording recording = Recording.builder()
                .id(recordingDto.getId())
                .studio(recordingDto.getStudio())
                .length(recordingDto.getLength())
                .year(recordingDto.getYear())
                .place(recordingDto.getPlace())
                .title(recordingDto.getTitle())
                .song(SongDtoMapper.map(recordingDto.getSong()))
                .artistList(recordingDto.getArtistList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // song Mapping
                        .toList())
                .groupList(recordingDto.getGroupList().stream()
                        .map(group ->
                                GroupDtoMapper.map(group)) // song Mapping
                        .toList())
                .build();
        return recording;
    }

    public static Recording mapWithNoId(RecordingDto recordingDto) {
        Recording recording = Recording.builder()
                .id(null)
                .studio(recordingDto.getStudio())
                .length(recordingDto.getLength())
                .year(recordingDto.getYear())
                .place(recordingDto.getPlace())
                .title(recordingDto.getTitle())
                .song(SongDtoMapper.map(recordingDto.getSong()))
                .artistList(recordingDto.getArtistList().stream()
                        .map(artist ->
                                ArtistDtoMapper.map(artist)) // song Mapping
                        .toList())
                .groupList(recordingDto.getGroupList().stream()
                        .map(group ->
                                GroupDtoMapper.map(group)) // song Mapping
                        .toList())
                .build();
        return recording;
    }

}
