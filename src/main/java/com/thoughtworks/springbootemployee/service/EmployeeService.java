package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.IllegalUpdateEmployeeException;
import com.thoughtworks.springbootemployee.Exception.NoSuchEmployeeException;
import com.thoughtworks.springbootemployee.constant.ExceptionMessage;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<EmployeeResponse> queryEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper.INSTANCE::employeeToEmployeeResponse).collect(Collectors.toList());
    }

    public List<EmployeeResponse> queryEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender).stream()
                .map(EmployeeMapper.INSTANCE::employeeToEmployeeResponse).collect(Collectors.toList());
    }

    public Page<EmployeeResponse> queryEmployeesByPage(int currentPage, int pageSize) {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = employees.stream().map(EmployeeMapper.INSTANCE::employeeToEmployeeResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(employeeResponses, PageRequest.of(currentPage - 1, pageSize), employeeResponses.size());
    }

    public EmployeeResponse queryEmployee(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return EmployeeMapper.INSTANCE.employeeToEmployeeResponse(employee);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        if (!id.equals(employee.getId())) {
            throw new IllegalUpdateEmployeeException(ExceptionMessage.ILLEGAL_UPDATE_EMPLOYEE.getErrorMsg());
        }
        Employee oldEmployee = employeeRepository.findById(id).orElse(null);
        if (oldEmployee == null) {
            throw new NoSuchEmployeeException(ExceptionMessage.NO_SUCH_EMPLOYEE.getErrorMsg());
        }
        if (employee.getName() != null) {
            oldEmployee.setName(employee.getName());
        }
        if (employee.getAge() > 0) {
            oldEmployee.setAge(employee.getAge());
        }
        if (employee.getGender() != null) {
            oldEmployee.setGender(employee.getGender());
        }
        if (employee.getSalary() != null) {
            oldEmployee.setSalary(employee.getSalary());
        }
        return employeeRepository.save(oldEmployee);
    }

    public void deleteEmployee(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new NoSuchEmployeeException(ExceptionMessage.NO_SUCH_EMPLOYEE.getErrorMsg());
        }
        employeeRepository.deleteById(employeeId);
    }

}
