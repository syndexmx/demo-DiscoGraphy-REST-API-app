package com.github.syndexmx.demodiscography.repository.mappers;

import com.github.syndexmx.demodiscography.domain.Recording;
import com.github.syndexmx.demodiscography.repository.entities.RecordingEntity;
import org.springframework.stereotype.Component;

@Component
public class RecordingEntityMapper {

    public static RecordingEntity map(Recording recording) {
        final RecordingEntity recordingEntity = RecordingEntity.builder()
                .recordingId(recording.getId())
                .studio(recording.getStudio())
                .length(recording.getLength())
                .year(recording.getYear())
                .place(recording.getPlace())
                .title(recording.getTitle())
                .song(SongEntityMapper.map(recording.getSong()))
                .artistList(recording.getArtistList().stream()
                        .map(artist ->
                                ArtistEntityMapper.map(artist)) // song Mapping
                        .toList())
                .groupList(recording.getGroupList().stream()
                        .map(group ->
                                GroupEntityMapper.map(group)) // song Mapping
                        .toList())
                .build();
        return recordingEntity;
    }

    public static Recording map(RecordingEntity recordingEntity) {
        Recording recording = Recording.builder()
                .id(recordingEntity.getRecordingId())
                .studio(recordingEntity.getStudio())
                .length(recordingEntity.getLength())
                .year(recordingEntity.getYear())
                .place(recordingEntity.getPlace())
                .title(recordingEntity.getTitle())
                .song(SongEntityMapper.map(recordingEntity.getSong()))
                .artistList(recordingEntity.getArtistList().stream()
                        .map(artist ->
                                ArtistEntityMapper.map(artist)) // song Mapping
                        .toList())
                .groupList(recordingEntity.getGroupList().stream()
                        .map(group ->
                                GroupEntityMapper.map(group)) // song Mapping
                        .toList())
                .build();
        return recording;
    }

}
