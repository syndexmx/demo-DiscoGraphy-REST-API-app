package com.github.syndexmx.demodiscography.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ArtistDto {

    private Long id;
    private String name;
    private String firstName;
    private String secondName;
    private String lastName;
    private String sex;
    private String birthDate;
    private String birthPlace;

}
