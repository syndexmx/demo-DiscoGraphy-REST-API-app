package com.github.syndexmx.demodiscography.domain;



import java.util.List;
import java.util.Long;


public class SongTestSupplierKit {

    private static Long id = Long.randomLong();

    private static Artistauthours song_FIELD_VALUE = Artistauthours.DEFAULTVALUE;
    private static Artistauthours song_STRING_MODIFIED = Artistauthours.ALTERNATIVEVALUE;

    public static Song getTestSong() {
        return Song.builder()
                .id(id)
                .authoursList(List.of())
                .authours(song_FIELD_VALUE)
                .build();
    }

    public static Song getModifiedTestSong() {
        return Song.builder()
                .id(id)
                .authours(song_STRING_MODIFIED)
                .authoursList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_Long = Long.randomLong();
    private static Artistauthours NON_EXISTANT_VALUE = Artistauthours.OTHERVALUE;

    public static Song getTestNonExistentSong( ) {
        return Song.builder()
                .id(NON_EXISTENT_Long)
                .authours(NON_EXISTANT_VALUE)
                .authoursList(List.of())
                .build();
    }

}
