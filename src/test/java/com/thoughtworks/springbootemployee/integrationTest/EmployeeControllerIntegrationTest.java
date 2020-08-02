package com.thoughtworks.springbootemployee.integrationTest;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void clearAllBefore() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @AfterEach
    private void clearAll() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    void should_return_employees_in_page_when_getEmployeeInPage_given_page_and_pageSize() throws Exception {
        //given
        Company companyOOCL = companyRepository.save(new Company(1, "oocl", 1, emptyList()));
        Employee employeeXiaoYi = employeeRepository.save(new Employee(1, "XIAOYI", 18, "Male", new BigDecimal(3000), companyOOCL.getId()));
        employeeRepository.save(new Employee(2, "xiaowang", 18, "Male", new BigDecimal(3000), companyOOCL.getId()));

        //when
        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON)
                .param("page", "1").param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(employeeXiaoYi.getId()));
    }

    @Test
    void should_return_male_employees_when_getEmployeesByGender_given_employees() throws Exception {
        //given
        Company companyOOCL = companyRepository.save(new Company(1, "oocl", 1, emptyList()));
        employeeRepository.save(new Employee(1, "Devin", 22, "male", new BigDecimal(9999), companyOOCL.getId()));
        employeeRepository.save(new Employee(2, "xiaohong", 22, "female", new BigDecimal(9999), companyOOCL.getId()));

        String gender = "male";

        //when
        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON)
                .param("gender", gender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].gender").value("male"));
    }

    @Test
    void should_return_employees_when_getEmployees_given_employees() throws Exception {
        //given
        Company companyOOCL = companyRepository.save(new Company(1, "oocl", 1, emptyList()));
        employeeRepository.save(new Employee(1, "Devin", 22, "male", new BigDecimal(9999), companyOOCL.getId()));
        employeeRepository.save(new Employee(2, "xiaohong", 22, "female", new BigDecimal(9999), companyOOCL.getId()));

        //when
        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void should_return_employee_by_id_when_getEmployee_given_id() throws Exception {
        //given
        Company companyOOCL = companyRepository.save(new Company(1, "oocl", 1, emptyList()));

        Employee employeeDevin = employeeRepository.save(new Employee(1, "Devin", 22, "male", new BigDecimal(9999), companyOOCL.getId()));
        employeeRepository.save(new Employee(2, "xiaohong", 22, "female", new BigDecimal(9999), companyOOCL.getId()));
        //when
        mockMvc.perform(get("/employees/" + employeeDevin.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(employeeDevin.getName()))
                .andExpect(jsonPath("$.age").value(employeeDevin.getAge()))
                .andExpect(jsonPath("$.gender").value(employeeDevin.getGender()))
                .andExpect(jsonPath("$.salary").isNumber())
                .andExpect(jsonPath("$.companyId").value(employeeDevin.getCompanyId()));
    }

    @Test
    void should_return_employee_when_addEmployee_given_employee() throws Exception {
        //given
        String employeeInfo = JSONObject.toJSONString(new Employee(1, "Devin", 18, "male", new BigDecimal(3000), 1));
        Company companyOOCL = companyRepository.save(new Company(1, "oocl", 1, emptyList()));

        //when
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                .content(employeeInfo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Devin"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").isNumber())
                .andExpect(jsonPath("$.companyId").value(companyOOCL.getId()));
    }

    @Test
    void should_return_employee_when_update_employee_given_id_and_employee() throws Exception {
        //given
        Company companyOOCL = companyRepository.save(new Company(1, "oocl", 1, emptyList()));
        Employee employeeDevin = employeeRepository.save(new Employee(1, "Devin", 22, "male", new BigDecimal(9999), companyOOCL.getId()));
        String updateEmployeeInfo = JSONObject.toJSONString(new Employee(employeeDevin.getId(), "Devin", 30, "male", new BigDecimal(3000), 1));

        //when
        mockMvc.perform(put("/employees/" + employeeDevin.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployeeInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Devin"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").isNumber())
                .andExpect(jsonPath("$.companyId").value(companyOOCL.getId()));
    }

    @Test
    void should_return_status_is_ok_when_deleteEmployee_given_id() throws Exception {
        //given
        Company oocl = companyRepository.save(new Company(1, "oocl", 1, emptyList()));
        Employee save = employeeRepository.save(new Employee(1, "Devin", 22, "male", new BigDecimal(9999), oocl.getId()));

        //when
        mockMvc.perform(delete("/employees/" + save.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
