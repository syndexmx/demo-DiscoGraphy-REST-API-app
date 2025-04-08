package com.github.syndexmx.demodiscography.domain;



import java.util.List;
import java.util.Random;


public class SongTestSupplierKit {

    private static Random random = new Random();
    private static Long id = random.nextLong();

    public static Song getTestSong() {
        return Song.builder()
                .id(id)
                .title("The Show Must Go On")
                .year(1991)
                .synonym("Show must go on")
                .authoursList(List.of())
                .build();
    }

    public static Song getModifiedTestSong() {
        return Song.builder()
                .id(id)
                .title("The Show Must Go On")
                .year(1991)
                .synonym("Show Must Go On")
                .authoursList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_Long = random.nextLong();

    public static Song getTestNonExistentSong( ) {
        return Song.builder()
                .id(NON_EXISTENT_Long)
                .authoursList(List.of())
                .build();
    }

}
