package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void should_equals_when_employeeRequest_to_employee_given_EmployeeRequest() {
        //given
        EmployeeRequest employeeRequest = new EmployeeRequest(1, "devin", 18, "male", new BigDecimal(3000), 1);

        //when
        Employee employee = employeeMapper.employeeRequestToEmployee(employeeRequest);

        //then
        assertEquals(employeeRequest.getId(), employee.getId());
        assertEquals(employeeRequest.getAge(), employee.getAge());
        assertEquals(employeeRequest.getCompanyId(), employee.getCompanyId());
        assertEquals(employeeRequest.getGender(), employee.getGender());
        assertEquals(employeeRequest.getName(), employee.getName());
        assertEquals(employeeRequest.getSalary(), employee.getSalary());
    }

    @Test
    void should_equals_when_employee_to_employeeRequest_given_employee() {
        //given
        Employee employee = new Employee(1, "devin", 18, "male", new BigDecimal(3000), 1);

        //when
        EmployeeRequest employeeRequest = employeeMapper.employeeToEmployeeRequest(employee);

        //then
        assertEquals(employeeRequest.getId(), employee.getId());
        assertEquals(employeeRequest.getAge(), employee.getAge());
        assertEquals(employeeRequest.getCompanyId(), employee.getCompanyId());
        assertEquals(employeeRequest.getGender(), employee.getGender());
        assertEquals(employeeRequest.getName(), employee.getName());
        assertEquals(employeeRequest.getSalary(), employee.getSalary());
    }

    @Test
    void should_equals_when_employee_to_employeeResponse_given_employee() {
        //given
        Employee employee = new Employee(1, "devin", 18, "male", new BigDecimal(3000), 1);

        //when
        EmployeeResponse employeeResponse = employeeMapper.employeeToEmployeeResponse(employee);

        //then
        assertEquals(employeeResponse.getId(), employee.getId());
        assertEquals(employeeResponse.getAge(), employee.getAge());
        assertEquals(employeeResponse.getCompanyId(), employee.getCompanyId());
        assertEquals(employeeResponse.getGender(), employee.getGender());
        assertEquals(employeeResponse.getName(), employee.getName());
        assertEquals(employeeResponse.getSalary(), employee.getSalary());
    }

    @Test
    void should_equals_when_employeeResponse_to_employee_given_employeeResponse() {
        //given
        EmployeeResponse employeeResponse = new EmployeeResponse(1, "devin", 18, "male", new BigDecimal(3000), 1);

        //when
        Employee employee = employeeMapper.employeeResponseToEmployee(employeeResponse);

        //then
        assertEquals(employeeResponse.getId(), employee.getId());
        assertEquals(employeeResponse.getAge(), employee.getAge());
        assertEquals(employeeResponse.getCompanyId(), employee.getCompanyId());
        assertEquals(employeeResponse.getGender(), employee.getGender());
        assertEquals(employeeResponse.getName(), employee.getName());
        assertEquals(employeeResponse.getSalary(), employee.getSalary());
    }
}
