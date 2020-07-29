package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.Exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceTest {

    private static final String MALE = "Male";
    private static final int EMPLOYEE_ID = 1;


    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeService employeeService;

    @Test
    void should_return_employees_when_getEmployees_given_() {
        //given
        when(employeeRepository.findAll()).thenReturn(asList());

        //when
        List<Employee> employees = employeeService.queryEmployees();

        //then
        assertNotNull(employees);

    }

    @Test
    void should_return_male_employees_when_getEmployeesByGender_given_gender_is_male() {
        //given
        when(employeeRepository.findAllByGender(any())).thenReturn(asList());
        //when
        List<Employee> employees = employeeService.queryEmployeesByGender(MALE);

        //then
        assertNotNull(employees);
    }

    @Test
    void should_return_employees_when_getEmployeesByPage_given_current_page_is_1_and_page_size_2() {
        //given
        Page<Employee> employeeswithPage = new PageImpl<Employee>(asList(new Employee(1, "xx", 18, "Male", new BigDecimal(2)),
                new Employee(2, "xx", 19, "Male", new BigDecimal(2))));
        when(employeeRepository.findAll(isA(PageRequest.class))).thenReturn(employeeswithPage);

        //when
        Page<Employee> employees = employeeService.queryEmployeesByPage(1, 2);

        //then
        assertEquals(2, employees.getSize());
    }

    @Test
    void should_return_employee_when_getEmployee_given_employee_id() {
        //given
        when(employeeRepository.findById(any())).thenReturn(Optional.of(new Employee(1, "xx", 18, "Male", new BigDecimal(2))));

        //when
        Employee employee = employeeService.queryEmployee(EMPLOYEE_ID);

        //then
        assertNotNull(employee);
    }

    @Test
    void should_return_employee_when_createEmployee_given_employee() {
        //given
        when(employeeRepository.save(any())).thenReturn(any());

        //when
        Employee employee = employeeService.createEmployee(new Employee(11, "tom chen", 18, "Male", new BigDecimal(9999)));

        //then
        verify(employeeRepository).save(any());
    }

    @Test
    void should_return_null_when_createEmployee_given_employee_exists() {
        //given
        when(employeeRepository.save(any())).thenReturn(null);
        //when
        Employee employee = employeeService.createEmployee(new Employee(1, "tom chen", 18, "Male", new BigDecimal(9999)));
        //then
        assertNull(employee);
    }

    @Test
    void should_return_employee_when_updateEmployee_given_exists_employee() throws Exception {
        //given
        Employee employee = new Employee(1, "xiaoshiyi", 18, "Male", new BigDecimal(5000));
        when(employeeRepository.save(any())).thenReturn(employee);
        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employee));

        //when
        Employee employeeUpdated = employeeService.updateEmployee(EMPLOYEE_ID, employee);

        //then
        assertNotNull(employeeUpdated);
    }

    @Test
    void should_return_employee_when_updateEmployee_given_new_employee() throws Exception {
        //given
        Employee employee = new Employee(2, "xiaoshiyi", 18, "Male", new BigDecimal(5000));

        //when
        Throwable exception = assertThrows(IllegalOperationException.class, () -> employeeService.updateEmployee(EMPLOYEE_ID, employee));

        //then
        assertEquals(IllegalOperationException.class,exception.getClass());
    }

    @Test
    void should_void_when_deleteEmployee_given_employee_id() throws NoSuchDataException {
        //given
        Employee employee = new Employee(1, "xiaoshiyi", 18, "Male", new BigDecimal(5000));
        when(employeeRepository.findById(eq(EMPLOYEE_ID))).thenReturn(
                Optional.of(employee)
        );

        //when
        employeeService.deleteEmployee(EMPLOYEE_ID);

        //then
        verify(employeeRepository, times(1)).deleteById(eq(EMPLOYEE_ID));
    }
}
