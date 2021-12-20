package com.critter_chronologer.controller;

import com.critter_chronologer.model.Pet;
import com.critter_chronologer.service.PetService;
import com.critter_chronologer.pet.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet(petDTO.getType(), petDTO.getName(),
                petDTO.getBirthDate(), petDTO.getNotes());
        PetDTO convertedPet;
        convertedPet = convertPetToPetDTO(petService.savePet(pet, petDTO.getOwnerId()));

        return convertedPet;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet;
        pet = petService.getPetById(petId);

        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets;
        pets = petService.getPetsByCustomerId(ownerId);

        return pets.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        return new PetDTO(pet.getId(),
                pet.getType(),
                pet.getName(),
                pet.getCustomer().getId(),
                pet.getBirthDate(),
                pet.getNotes());
    }
}
