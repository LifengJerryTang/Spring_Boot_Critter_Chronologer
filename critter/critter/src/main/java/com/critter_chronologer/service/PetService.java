package com.critter_chronologer.service;

import com.critter_chronologer.repository.PetRepository;
import com.critter_chronologer.model.Customer;
import com.critter_chronologer.model.Pet;
import com.critter_chronologer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;
    
    public Pet savePet(Pet pet, Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List<Pet> pets = new ArrayList<>();

        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);

        return pet;
    }

    public List<Pet> getPetsByCustomerId(long customerId) {
        return petRepository.findPetByCustomerId(customerId);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long petId) {
        return petRepository.getOne(petId);
    }
}
