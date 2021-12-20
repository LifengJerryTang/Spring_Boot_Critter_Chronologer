package com.critter_chronologer.repository;

import com.critter_chronologer.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDaysAvailable(DayOfWeek dayOfWeek);
}
