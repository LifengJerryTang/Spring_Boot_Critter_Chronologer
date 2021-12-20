package com.critter_chronologer.repository;

import com.critter_chronologer.model.Pet;
import com.critter_chronologer.model.Schedule;
import com.critter_chronologer.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPets(Pet pet);
    List<Schedule> findByEmployee(Employee employee);
}
