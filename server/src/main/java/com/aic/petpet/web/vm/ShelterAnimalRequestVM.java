package com.aic.petpet.web.vm;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ShelterAnimalRequestVM {

    @JsonSetter(nulls= Nulls.SKIP)
    private String animalName = "%";
    @JsonSetter(nulls= Nulls.SKIP)
    private String animalNumber = "%";

    private String animalType;

    private String animalArea;

    private String animalGender;

    private String animalAge;
    @JsonSetter(nulls= Nulls.SKIP)
    private String animalCategory = "%";
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonSetter(nulls = Nulls.SKIP)
    private LocalDate animalDateFrom = LocalDate.of(1900, 1, 1);


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonSetter(nulls = Nulls.SKIP)
    private LocalDate animalDateTo = LocalDate.of(9999,12,31);

}
