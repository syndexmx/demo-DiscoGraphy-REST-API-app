package com.github.syndexmx.demodiscography.domain;



import java.util.List;
import java.util.Random;


public class AlbumTestSupplierKit {

    private static Random random = new Random();
    private static Long id = random.nextLong();

    public static Album getTestAlbum() {
        return Album.builder()
                .id(id)
                .artistList(List.of())
                .build();
    }

    public static Album getModifiedTestAlbum() {
        return Album.builder()
                .id(id)
                .recordingList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_LONG = random.nextLong();

    public static Album getTestNonExistentAlbum( ) {
        return Album.builder()
                .id(NON_EXISTENT_LONG)
                .build();
    }

}
