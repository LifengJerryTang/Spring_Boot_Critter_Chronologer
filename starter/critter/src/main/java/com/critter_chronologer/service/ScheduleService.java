package com.critter_chronologer.service;

import com.critter_chronologer.model.Customer;
import com.critter_chronologer.model.Employee;
import com.critter_chronologer.model.Pet;
import com.critter_chronologer.model.Schedule;
import com.critter_chronologer.repository.CustomerRepository;
import com.critter_chronologer.repository.EmployeeRepository;
import com.critter_chronologer.repository.PetRepository;
import com.critter_chronologer.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Pet> pets = petRepository.findAllById(petIds);
        List<Employee> employees = employeeRepository.findAllById(employeeIds);

        schedule.setPets(pets);
        schedule.setEmployee(employees);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getEmployeeSchedule(Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.findByEmployee(employee);
    }

    public List<Schedule> getPetSchedule(Long petId) {
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.findByPets(pet);
    }

    public List<Schedule> getCustomerSchedule(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List<Schedule> schedules = new ArrayList<>();
        customer.getPets().forEach(pet -> schedules.addAll(scheduleRepository.findByPets(pet)));
        return schedules;
    }

}
