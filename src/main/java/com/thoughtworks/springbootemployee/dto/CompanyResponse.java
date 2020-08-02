package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

    private Integer id;

    private String companyName;

    private int employeeNumber;

    private List<Employee> employees;
}
