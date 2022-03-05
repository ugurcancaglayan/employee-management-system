package com.caglayan.ems.service.impl;

import com.caglayan.ems.model.Department;
import com.caglayan.ems.model.Employee;
import com.caglayan.ems.model.dto.EmployeeDto;
import com.caglayan.ems.repository.AddressRepository;
import com.caglayan.ems.repository.EmployeeRepository;
import com.caglayan.ems.service.DepartmentService;
import com.caglayan.ems.service.EmployeeService;
import com.caglayan.ems.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ValidationService validationService;
    private final DepartmentService departmentService;
    private final AddressRepository addressService;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(EmployeeDto employeeDto) {
        Department department = departmentService.getById(employeeDto.getDepartment().getId());

        if (department != null) {
            if (!validationService.phoneNumberValidation(employeeDto.getPhoneNumber())) {
                throw new IllegalArgumentException("Phone number's format is not properly!");
            } else {
                Employee employee = Employee.builder()
                        .name(employeeDto.getName())
                        .mail(employeeDto.getMail())
                        .phoneNumber(employeeDto.getPhoneNumber())
                        .address(employeeDto.getAddressList())
                        .department(department)
                        .build();

                return employeeRepository.save(employee);
            }
        } else
            throw new IllegalArgumentException("Information about the employee is incorrect!");
    }

    public Employee updateEmployee(Employee employee) {
        Optional<Employee> oldEmployee = employeeRepository.findById(employee.getId());

        if (oldEmployee.isPresent()) {
            return employeeRepository.save(employee);
        } else
            throw new NullPointerException("Employee Not Found");
    }

    public void deleteManager(long id) {
        employeeRepository.deleteById(id);
    }

}
