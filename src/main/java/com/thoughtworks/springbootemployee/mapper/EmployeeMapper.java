package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;


@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeResponse employeeToEmployeeResponse(Employee employee);


    Employee employeeResponseToEmployee(EmployeeResponse employeeResponse);


    EmployeeRequest employeeToEmployeeRequest(Employee employee);


    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);
}
