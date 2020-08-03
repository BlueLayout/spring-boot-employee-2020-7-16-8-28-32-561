package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.IllegalUpdateEmployeeException;
import com.thoughtworks.springbootemployee.Exception.NoSuchEmployeeException;
import com.thoughtworks.springbootemployee.constant.ExceptionMessage;
import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.utils.PageUtils;
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
        List<EmployeeResponse> employeeResponsesSubList = new PageUtils<EmployeeResponse>().getPage(employeeResponses,currentPage,pageSize);
        return new PageImpl<>(employeeResponsesSubList, PageRequest.of(currentPage, pageSize), employeeResponsesSubList.size());
    }

    public EmployeeResponse queryEmployee(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return EmployeeMapper.INSTANCE.employeeToEmployeeResponse(employee);
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = EmployeeMapper.INSTANCE.employeeRequestToEmployee(employeeRequest);
        Employee employeeSave = employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.employeeToEmployeeResponse(employeeSave);
    }

    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest employeeRequest) {
        if (!id.equals(employeeRequest.getId())) {
            throw new IllegalUpdateEmployeeException(ExceptionMessage.ILLEGAL_UPDATE_EMPLOYEE.getErrorMsg());
        }
        Employee oldEmployee = employeeRepository.findById(id).orElse(null);
        if (oldEmployee == null) {
            throw new NoSuchEmployeeException(ExceptionMessage.NO_SUCH_EMPLOYEE.getErrorMsg());
        }
        if (employeeRequest.getName() != null) {
            oldEmployee.setName(employeeRequest.getName());
        }
        if (employeeRequest.getAge() > 0) {
            oldEmployee.setAge(employeeRequest.getAge());
        }
        if (employeeRequest.getGender() != null) {
            oldEmployee.setGender(employeeRequest.getGender());
        }
        if (employeeRequest.getSalary() != null) {
            oldEmployee.setSalary(employeeRequest.getSalary());
        }
        return EmployeeMapper.INSTANCE.employeeToEmployeeResponse(employeeRepository.save(oldEmployee));
    }

    public void deleteEmployee(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new NoSuchEmployeeException(ExceptionMessage.NO_SUCH_EMPLOYEE.getErrorMsg());
        }
        employeeRepository.deleteById(employeeId);
    }

}
