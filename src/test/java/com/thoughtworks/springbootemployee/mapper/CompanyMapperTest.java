package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CompanyMapperTest {

    @Test
    void should_equals_when_company_to_companyResponse_given_company() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "devin", 18, "male", new BigDecimal(3000), 1));
        Company company = new Company(1, "oocl", 1, employees);

        //when
        CompanyResponse companyResponse = CompanyMapper.INSTANCE.companyToCompanyResponse(company);

        //then
        assertEquals(company.getId(), companyResponse.getId());
        assertEquals(company.getEmployees().size(), companyResponse.getEmployees().size());
        assertEquals(company.getCompanyName(), companyResponse.getCompanyName());
        assertEquals(company.getEmployeeNumber(), companyResponse.getEmployeeNumber());
    }

    @Test
    void should_equals_when_companyResponse_to_company_given_companyResponse() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "devin", 18, "male", new BigDecimal(3000), 1));
        CompanyResponse companyResponse = new CompanyResponse(1, "oocl", 1, employees);

        //when
        Company company = CompanyMapper.INSTANCE.companyResponseToCompany(companyResponse);

        //then
        assertEquals(company.getId(), companyResponse.getId());
        assertEquals(company.getEmployees().size(), companyResponse.getEmployees().size());
        assertEquals(company.getCompanyName(), companyResponse.getCompanyName());
        assertEquals(company.getEmployeeNumber(), companyResponse.getEmployeeNumber());
    }

    @Test
    void should_equals_when_company_to_companyRequest_given_company() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "devin", 18, "male", new BigDecimal(3000), 1));
        Company company = new Company(1, "oocl", 1, employees);

        //when
        CompanyRequest companyRequest = CompanyMapper.INSTANCE.companyToCompanyRequest(company);

        //then
        assertEquals(company.getId(), companyRequest.getId());
        assertEquals(company.getEmployees().size(), companyRequest.getEmployees().size());
        assertEquals(company.getCompanyName(), companyRequest.getCompanyName());
        assertEquals(company.getEmployeeNumber(), companyRequest.getEmployeeNumber());

    }

    @Test
    void should_equals_when_companyRequest_to_company_given_companyRequest() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "devin", 18, "male", new BigDecimal(3000), 1));
        CompanyRequest companyRequest = new CompanyRequest(1, "oocl", 1, employees);

        //when
        Company company = CompanyMapper.INSTANCE.companyRequestToCompany(companyRequest);

        //then
        assertEquals(company.getId(), companyRequest.getId());
        assertEquals(company.getEmployees().size(), companyRequest.getEmployees().size());
        assertEquals(company.getCompanyName(), companyRequest.getCompanyName());
        assertEquals(company.getEmployeeNumber(), companyRequest.getEmployeeNumber());
    }
}
