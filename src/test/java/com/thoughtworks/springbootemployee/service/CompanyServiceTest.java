package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.IllegalUpdateCompanyException;
import com.thoughtworks.springbootemployee.Exception.NoSuchCompanyException;
import com.thoughtworks.springbootemployee.constant.ExceptionMessage;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CompanyServiceTest {

    private static final int COMPANY_ID = 1;
    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepository companyRepository;

    @Test
    void should_return_companies_when_getCompanies_given_() {
        //given
        when(companyRepository.findAll()).thenReturn(getMockCompanies());

        //when
        List<CompanyResponse> companies = companyService.getCompanies();

        //then
        assertEquals(10, companies.size());

    }

    @Test
    void should_return_companies_when_getCompanies_given_page_and_pageSize() {
        //given
        int page = 1;
        int pageSize = 3;
        Page<Company> companies = new PageImpl<Company>(Collections.emptyList());
        when(companyRepository.findAll(isA(PageRequest.class))).thenReturn(companies);
        //when
        companyService.getCompaniesPage(page, pageSize);
        //then
        verify(companyRepository).findAll(isA(PageRequest.class));
    }

    @Test
    void should_return_company_when_getCompany_given_company_id() {
        //given

        when(companyRepository.findById(eq(COMPANY_ID))).thenReturn(Optional.of(new Company()));
        //when

        companyService.getCompany(COMPANY_ID);

        //then
        verify(companyRepository).findById(eq(COMPANY_ID));
    }

    @Test
    void should_return_Employees_when_getEmployees_given_company_id() {
        //given
        when(companyRepository.findById(eq(COMPANY_ID))).thenReturn(Optional.of(getMockCompany()));

        //when
        List<Employee> employees = companyService.getEmployees(COMPANY_ID);
        //then
        assertEquals(10, employees.size());
    }

    @Test
    void should_return_employee_when_create_company_given_company() {
        //given
        when(companyRepository.save(any())).thenReturn(any());

        //when
        Company company = companyService.createCompany(getMockCompany());

        //then
        verify(companyRepository).save(any());
    }

    @Test
    void should_return_company_when_updateCompany_given_exists_company() throws Exception {
        //given
        when(companyRepository.save(any())).thenReturn(getMockCompany());
        when(companyRepository.findById(COMPANY_ID)).thenReturn(Optional.of(getMockCompany()));

        //when
        Company companyUpdated = companyService.updateCompany(COMPANY_ID, getMockCompany());
        //then
        verify(companyRepository).findById(COMPANY_ID);
        verify(companyRepository).save(isA(Company.class));
    }

    @Test
    void should_throw_ILLEGALECXEPTION_when_updateCompany_given_() throws Exception {
        //given

        //when
        Throwable exception = assertThrows(IllegalUpdateCompanyException.class,
                () -> companyService.updateCompany(2, getMockCompany()));

        //then
        assertEquals(ExceptionMessage.ILLEGAL_UPDATE_COMPANY.getErrorMsg(), exception.getMessage());
    }

    @Test
    void should_throw_NOSUCHDATAEXCEPTION_when_updateCompany_given_() {
        //given
        when(companyRepository.findById(eq(COMPANY_ID))).thenReturn(Optional.empty());
        //when
        Throwable exception = assertThrows(NoSuchCompanyException.class, () -> companyService.updateCompany(COMPANY_ID, getMockCompany()));
        //then
        assertEquals(ExceptionMessage.NO_SUCH_COMPANY.getErrorMsg(), exception.getMessage());
    }

    @Test
    void should_void_when_deleteCompany_given_company_id() {
        //given
        when(companyRepository.findById(eq(COMPANY_ID))).thenReturn(Optional.of(getMockCompany()));
        //when
        companyService.deleteCompany(COMPANY_ID);
        //then
        verify(companyRepository).deleteById(eq(COMPANY_ID));
    }

    @Test
    void should_throw_NoSuchDataException_when_deleteCompany_given_company_no_exists() {
        //given
        when(companyRepository.findById(eq(COMPANY_ID))).thenReturn(Optional.empty());
        //when
        Throwable exception = assertThrows(NoSuchCompanyException.class, () -> companyService.deleteCompany(COMPANY_ID));
        //then
        assertEquals(ExceptionMessage.NO_SUCH_COMPANY.getErrorMsg(), exception.getMessage());

    }

    private List<Company> getMockCompanies() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "xiaoyi", 18, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(2, "xiaoer", 18, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(3, "xiaosan", 19, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(4, "xiaosi", 19, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(5, "xiaowu", 20, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(6, "xiaoliu", 20, "Female", new BigDecimal(3000), 1));
        employees.add(new Employee(7, "xiaoqi", 21, "Female", new BigDecimal(3000), 1));
        employees.add(new Employee(8, "xiaoba", 21, "Female", new BigDecimal(3000), 1));
        employees.add(new Employee(9, "xiaojiu", 18, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(10, "xiaoshi", 18, "Male", new BigDecimal(3000), 1));
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "Mm", 10, employees));
        companies.add(new Company(2, "Aa", 10, employees));
        companies.add(new Company(3, "Bb", 10, employees));
        companies.add(new Company(4, "Cc", 10, employees));
        companies.add(new Company(5, "Dd", 10, employees));
        companies.add(new Company(6, "Ff", 10, employees));
        companies.add(new Company(7, "Gg", 10, employees));
        companies.add(new Company(8, "Ll", 10, employees));
        companies.add(new Company(9, "Pp", 10, employees));
        companies.add(new Company(10, "Uu", 10, employees));
        return companies;
    }

    private Company getMockCompany() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "xiaoyi", 18, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(2, "xiaoer", 18, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(3, "xiaosan", 19, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(4, "xiaosi", 19, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(5, "xiaowu", 20, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(6, "xiaoliu", 20, "Female", new BigDecimal(3000), 1));
        employees.add(new Employee(7, "xiaoqi", 21, "Female", new BigDecimal(3000), 1));
        employees.add(new Employee(8, "xiaoba", 21, "Female", new BigDecimal(3000), 1));
        employees.add(new Employee(9, "xiaojiu", 18, "Male", new BigDecimal(3000), 1));
        employees.add(new Employee(10, "xiaoshi", 18, "Male", new BigDecimal(3000), 1));
        return new Company(1, "Mm", 10, employees);
    }
}
