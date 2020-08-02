package com.thoughtworks.springbootemployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Integer id;

    private String name;

    private int age;

    private String gender;

    private BigDecimal salary;

    private int companyId;
}
