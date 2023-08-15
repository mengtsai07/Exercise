package com.aic.petpet.repository;

import com.aic.petpet.domain.ShelterAnimal;
import com.aic.petpet.web.vm.ShelterAnimalRequestVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ShelterAnimalRepository extends JpaRepository<ShelterAnimal,Integer> {

    @Query(value = "SELECT sa FROM ShelterAnimal sa WHERE " +
            "(sa.animalType = :#{#shelterAnimal.animalType} OR :#{#shelterAnimal.animalType} IS NULL) AND " +
            "(sa.animalArea = :#{#shelterAnimal.animalArea} OR :#{#shelterAnimal.animalArea} IS NULL) AND " +
            "(sa.animalGender = :#{#shelterAnimal.animalGender} OR :#{#shelterAnimal.animalGender} IS NULL) AND " +
            "(UPPER(sa.animalName) LIKE UPPER(CONCAT('%', :#{#shelterAnimal.animalName}, '%'))) AND " +
            "(UPPER(sa.animalNumber) LIKE UPPER(CONCAT('%', :#{#shelterAnimal.animalNumber}, '%'))) AND " +
            "(sa.animalAge = :#{#shelterAnimal.animalAge} OR :#{#shelterAnimal.animalAge} IS NULL) AND " +
            "(UPPER(sa.animalCategory) LIKE UPPER(CONCAT('%', :#{#shelterAnimal.animalCategory}, '%'))) AND " +
            "(sa.animalDate BETWEEN :#{#shelterAnimal.animalDateFrom} AND :#{#shelterAnimal.animalDateTo})")




    Page<ShelterAnimal> findShelterAnimals(Pageable pageable, @Param("shelterAnimal") ShelterAnimalRequestVM Animal);




}
