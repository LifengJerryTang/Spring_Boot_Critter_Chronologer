package com.critter_chronologer.controller;

import com.critter_chronologer.model.Customer;
import com.critter_chronologer.service.EmployeeService;
import com.critter_chronologer.user.CustomerDTO;
import com.critter_chronologer.user.EmployeeRequestDTO;
import com.critter_chronologer.model.Employee;
import com.critter_chronologer.model.Pet;
import com.critter_chronologer.service.CustomerService;
import com.critter_chronologer.user.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getPhoneNumber(), customerDTO.getNotes());
        List<Long> petIds = customerDTO.getPetIds();

        return convertCustomerToCustomerDTO(customerService.saveCustomer(customer, petIds));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream().map(this::convertCustomerToCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPetId(petId);
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getId(),
                employeeDTO.getName(),
                employeeDTO.getSkills(), employeeDTO.getDaysAvailable());
        EmployeeDTO convertedEmployee = convertEmployeeToEmployeeDTO(employeeService.saveEmployee(employee));

        return convertedEmployee;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getEmployeesByService(employeeDTO.getDate(), employeeDTO.getSkills());
        return employees.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        return new CustomerDTO(customer.getId(), customer.getName(),
                customer.getPhoneNumber(), customer.getNotes(), petIds);
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getSkills(), employee.getDaysAvailable());
    }

}
