package com.aic.petpet.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "shelterAnimal")
public class ShelterAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer animalId;
    private String animalName;
    private String animalType;
    private String animalCategory;
    private String animalColor;
    private String animalGender;
    private LocalDate animalDate;
    private String animalAge;
    @Lob
    private String  animalPhoto1;
    @Lob
    private String  animalPhoto2;
    @Lob
    private String  animalPhoto3;
    private String animalDescribe;
//    @Column(insertable = false)
//    private Integer ifAdopted;
    private String animalNumber;
    private String shelterName;
    private String animalArea;
}



