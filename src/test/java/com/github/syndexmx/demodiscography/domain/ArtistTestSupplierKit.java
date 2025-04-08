package com.github.syndexmx.demodiscography.domain;



import com.github.syndexmx.demodiscography.domain.enums.Sex;

import java.util.List;
import java.util.Long;


public class ArtistTestSupplierKit {

    private static Long id = Long.randomLong();

    private static Sex artist_FIELD_VALUE = Sex.DEFAULTVALUE;
    private static Sex artist_STRING_MODIFIED = Sex.ALTERNATIVEVALUE;

    public static Artist getTestArtist() {
        return Artist.builder()
                .id(id)
                .sexList(List.of())
                .sex(artist_FIELD_VALUE)
                .build();
    }

    public static Artist getModifiedTestArtist() {
        return Artist.builder()
                .id(id)
                .sex(artist_STRING_MODIFIED)
                .sexList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_Long = Long.randomLong();
    private static Sex NON_EXISTANT_VALUE = Sex.OTHERVALUE;

    public static Artist getTestNonExistentArtist( ) {
        return Artist.builder()
                .id(NON_EXISTENT_Long)
                .sex(NON_EXISTANT_VALUE)
                .sexList(List.of())
                .build();
    }

}
