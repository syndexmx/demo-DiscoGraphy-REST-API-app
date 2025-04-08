package com.github.syndexmx.demodiscography.domain;



import java.util.List;
import java.util.Long;


public class AlbumTestSupplierKit {

    private static Long id = Long.randomLong();

    private static RecordingType album_FIELD_VALUE = RecordingType.DEFAULTVALUE;
    private static RecordingType album_STRING_MODIFIED = RecordingType.ALTERNATIVEVALUE;

    public static Album getTestAlbum() {
        return Album.builder()
                .id(id)
                .artistList(List.of())
                .recordings(album_FIELD_VALUE)
                .build();
    }

    public static Album getModifiedTestAlbum() {
        return Album.builder()
                .id(id)
                .recordings(album_STRING_MODIFIED)
                .recordingsList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_Long = Long.randomLong();
    private static RecordingType NON_EXISTANT_VALUE = RecordingType.OTHERVALUE;

    public static Album getTestNonExistentAlbum( ) {
        return Album.builder()
                .id(NON_EXISTENT_Long)
                .recordings(NON_EXISTANT_VALUE)
                .recordingsList(List.of())
                .build();
    }

}
