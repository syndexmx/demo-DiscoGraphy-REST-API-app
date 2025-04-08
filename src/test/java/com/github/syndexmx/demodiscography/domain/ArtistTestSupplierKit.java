package com.github.syndexmx.demodiscography.domain;

import com.github.syndexmx.demodiscography.domain.enums.Sex;

import java.time.LocalDate;
import java.util.Random;

public class ArtistTestSupplierKit {

    private static Random random = new Random();
    private static Long id = random.nextLong();

    public static Artist getTestArtist() {
        return Artist.builder()
                .id(id)
                .name("Freddie Mercury")
                .firstName("Farrokh")
                .secondName("")
                .lastName("Bulsara")
                .birthDate(LocalDate.parse("1946-09-05"))
                .birthPlace("Zanzibar")
                .sex(Sex.MALE)
                .build();
    }

    public static Artist getModifiedTestArtist() {
        return Artist.builder()
                .id(id)
                .name("Freddie Mercury")
                .firstName("Farrokh")
                .secondName("Freddie Mercury")
                .lastName("Bulsara")
                .birthDate(LocalDate.parse("1946-09-05"))
                .birthPlace("Zanzibar")
                .sex(Sex.MALE)
                .build();
    }

    private static Long NON_EXISTENT_Long = random.nextLong();

    public static Artist getTestNonExistentArtist( ) {
        return Artist.builder()
                .id(NON_EXISTENT_Long)
                .build();
    }

}
