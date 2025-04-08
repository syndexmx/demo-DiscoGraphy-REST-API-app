package com.github.syndexmx.demodiscography.domain;

import com.github.syndexmx.demodiscography.domain.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Artist {

    private Long id;
    private String secondName;
    private LocalDate birthDate;
    private String firstName;
    private String name;
    private String birthPlace;
    private String lastName;
    private Sex sex;

}
