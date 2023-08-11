package com.aic.petpet.web.controller;

import com.aic.petpet.domain.ShelterAnimal;
import com.aic.petpet.service.ShelterAnimalService;
import com.aic.petpet.web.vm.ShelterAnimalRequestVM;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
//@RequestMapping("/shelter")
public class ShelterAnimalController {
    private final ShelterAnimalService service;


    @PostMapping("/save")
    public Object saveAnimal(@RequestBody ShelterAnimal shelterAnimal){
        return this.service.saveAnimal(shelterAnimal);
    }

    @DeleteMapping("/delete")
//    @PostMapping?
    public void deleteAnimal(@RequestBody ShelterAnimal shelterAnimal){
        this.service.delete(shelterAnimal);
    }

    @PostMapping("/findByanimalId/{animalId}")
    public ShelterAnimal read(@PathVariable Integer animalId) {
        ShelterAnimal shelterAnimal = service.findByAnimalId(animalId).orElse(null);
        service.findByAnimalId(animalId);
        return shelterAnimal;}

    @GetMapping("/shelteranimal")
    public Object findAnimal(Pageable pageable){
        return this.service.findAll(pageable);
    }
    @GetMapping("/search")
    public  Object search(Pageable pageable,@ModelAttribute ShelterAnimalRequestVM shelterAnimal){
        return  this.service.search(pageable, shelterAnimal);
    }

}




