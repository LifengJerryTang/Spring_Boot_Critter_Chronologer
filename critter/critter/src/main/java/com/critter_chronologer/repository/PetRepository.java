package com.critter_chronologer.repository;

import com.critter_chronologer.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findPetByCustomerId(Long customerId);
}
