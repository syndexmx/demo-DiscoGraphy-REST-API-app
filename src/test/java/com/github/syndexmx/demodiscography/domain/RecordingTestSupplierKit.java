package com.github.syndexmx.demodiscography.domain;



import java.util.List;
import java.util.Random;


public class RecordingTestSupplierKit {

    private static Random random = new Random();
    private static Long id = random.nextLong();


    public static Recording getTestRecording() {
        return Recording.builder()
                .id(id)
                .song(SongTestSupplierKit.getTestSong())
                .place("Switzeland, Montreux")
                .studio("Mountain")
                .length(4 * 60 + 31)
                .artistList(List.of())
                .artistList(List.of())
                .build();
    }

    public static Recording getModifiedTestRecording() {
        return Recording.builder()
                .id(id)
                .song(SongTestSupplierKit.getTestSong())
                .place("Montreux, Switzeland")
                .studio("Mountain")
                .length(4 * 60 + 31)
                .artistList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_Long = random.nextLong();

    public static Recording getTestNonExistentRecording( ) {
        return Recording.builder()
                .id(NON_EXISTENT_Long)
                .artistList(List.of())
                .build();
    }

}
