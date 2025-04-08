package com.github.syndexmx.demodiscography.domain;



import java.util.List;
import java.util.Long;


public class RecordingTestSupplierKit {

    private static Long id = Long.randomLong();

    private static SongType recording_FIELD_VALUE = SongType.DEFAULTVALUE;
    private static SongType recording_STRING_MODIFIED = SongType.ALTERNATIVEVALUE;

    public static Recording getTestRecording() {
        return Recording.builder()
                .id(id)
                .artistList(List.of())
                .song(recording_FIELD_VALUE)
                .build();
    }

    public static Recording getModifiedTestRecording() {
        return Recording.builder()
                .id(id)
                .song(recording_STRING_MODIFIED)
                .artistList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_Long = Long.randomLong();
    private static SongType NON_EXISTANT_VALUE = SongType.OTHERVALUE;

    public static Recording getTestNonExistentRecording( ) {
        return Recording.builder()
                .id(NON_EXISTENT_Long)
                .song(NON_EXISTANT_VALUE)
                .artistList(List.of())
                .build();
    }

}
