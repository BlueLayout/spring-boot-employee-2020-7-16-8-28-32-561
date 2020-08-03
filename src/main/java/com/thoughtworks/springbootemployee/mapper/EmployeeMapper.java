package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;


@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS)
public interface EmployeeMapper {

    EmployeeResponse employeeToEmployeeResponse(Employee employee);

    Employee employeeResponseToEmployee(EmployeeResponse employeeResponse);

    EmployeeRequest employeeToEmployeeRequest(Employee employee);


    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);
}
