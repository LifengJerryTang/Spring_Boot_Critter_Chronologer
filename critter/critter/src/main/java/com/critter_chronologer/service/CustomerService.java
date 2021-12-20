package com.critter_chronologer.service;

import com.critter_chronologer.model.Customer;
import com.critter_chronologer.model.Pet;
import com.critter_chronologer.repository.CustomerRepository;
import com.critter_chronologer.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> customerPets = new ArrayList<>();
        if (petIds != null && !petIds.isEmpty()) {
            customerPets = petIds.stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList());
        }
        customer.setPets(customerPets);
        return customerRepository.save(customer);
    }

    public Customer getCustomerByPetId(Long petId) {
        return petRepository.getOne(petId).getCustomer();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
