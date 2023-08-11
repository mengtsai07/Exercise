package com.aic.petpet.service;

import com.aic.petpet.repository.ShelterAnimalRepository;
import com.aic.petpet.domain.ShelterAnimal;
import com.aic.petpet.web.vm.ShelterAnimalRequestVM;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ShelterAnimalService {
    private final ShelterAnimalRepository repo;

    public Page<ShelterAnimal> findAll(Pageable pageable){
        return this.repo.findAll(pageable);
    }

    public Page<ShelterAnimal> search(Pageable pageable, ShelterAnimalRequestVM shelterAnimal){
        this.format(shelterAnimal);
        return this.repo.findShelterAnimals(pageable, shelterAnimal);
    }
    public ShelterAnimalRequestVM format(ShelterAnimalRequestVM shelterAnimal) {
        if(!Objects.equals(shelterAnimal.getAnimalName(), "%")) shelterAnimal.setAnimalName("%" + shelterAnimal.getAnimalName() + "%");
        if(!Objects.equals(shelterAnimal.getAnimalNumber(), "%")) shelterAnimal.setAnimalNumber("%" + shelterAnimal.getAnimalNumber() + "%");
        return shelterAnimal;
    }
    public ShelterAnimal saveAnimal(ShelterAnimal shelterAnimal){
        return this.repo.save(shelterAnimal);
    }

    public void delete(ShelterAnimal shelterAnimal){
        this.repo.deleteById(shelterAnimal.getAnimalId());
    }

    public Optional<ShelterAnimal> findByAnimalId(Integer animalId){
        return this.repo.findById(animalId);}

}


