package com.critter_chronologer.service;

import com.critter_chronologer.repository.EmployeeRepository;
import com.critter_chronologer.model.Employee;
import com.critter_chronologer.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByService(LocalDate date, Set<EmployeeSkill> skills){
        return employeeRepository
                .findByDaysAvailable(date.getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> days, Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(days);
        employeeRepository.save(employee);
    }

}
