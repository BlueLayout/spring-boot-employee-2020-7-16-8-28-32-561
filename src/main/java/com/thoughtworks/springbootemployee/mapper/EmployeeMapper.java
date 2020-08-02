package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeResponse employeeToEmployeeResponse(Employee employee);


    Employee employeeResponseToEmployee(EmployeeResponse employeeResponse);


    EmployeeRequest employeeToEmployeeRequest(Employee employee);


    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);
}
