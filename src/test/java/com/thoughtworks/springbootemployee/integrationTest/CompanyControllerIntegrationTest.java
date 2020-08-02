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
public class CompanyControllerIntegrationTest {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    private void clearAllBefore() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @AfterEach
    private void clearAll() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_companies_in_page_when_get_given_page_and_pageSize() throws Exception {
        //given
        Company company = new Company(1, "alibaba", 1, emptyList());
        companyRepository.save(company);

        //when
        mockMvc.perform(get("/companies").contentType(MediaType.APPLICATION_JSON)
                .param("page", "1").param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1));

    }

    @Test
    void should_return_all_companies_when_get_given_nothing() throws Exception {
        //given
        companyRepository.save(new Company(1, "alibaba", 1, emptyList()));
        companyRepository.save(new Company(2, "tencent", 1, emptyList()));

        //when
        mockMvc.perform(get("/companies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber());

    }

    @Test
    void should_return_company_has_id_when_get_given_id() throws Exception {
        //given
        companyRepository.save(new Company(1, "alibaba", 1, emptyList()));
        Company companyOOCL = companyRepository.save(new Company(2, "oocl", 1, emptyList()));

        //when
        mockMvc.perform(get("/companies/" + companyOOCL.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(companyOOCL.getId()))
                .andExpect(jsonPath("$.companyName").value(companyOOCL.getCompanyName()))
                .andExpect(jsonPath("$.employeeNumber").value(companyOOCL.getEmployeeNumber()));
    }

    @Test
    void should_return_employee_in_this_company_when_get_given_id_of_company() throws Exception {
        //given
        Company companyOOCL = companyRepository.save(new Company(1, "oocl", 1, emptyList()));
        Employee employeeJack = employeeRepository.save(new Employee(1, "jack", 23, "male", new BigDecimal(9999), 1));

        //when
        mockMvc.perform(get("/companies/" + companyOOCL.getId() + "/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(employeeJack.getId()))
                .andExpect(jsonPath("$[0].name").value(employeeJack.getName()))
                .andExpect(jsonPath("$[0].age").value(employeeJack.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employeeJack.getGender()))
                .andExpect(jsonPath("$[0].salary").isNumber())
                .andExpect(jsonPath("$[0].companyId").value(employeeJack.getCompanyId()));

    }

    @Test
    void should_return_company_when_post_given_company() throws Exception {
        //given
        String companyInfo = JSONObject.toJSONString(new Company(1,"alibaba",0,emptyList()));

        //when
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON)
                .content(companyInfo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    void should_return_updated_company_when_put_given_company_id() throws Exception {
        //given
        String companyInfo = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"alibaba\",\n" +
                "    \"employeeNumber\": 0,\n" +
                "    \"employees\": []\n" +
                "}";
        Company company = new Company(1, "tencent", 1, emptyList());
        Company companySaved = companyRepository.save(company);

        //when
        mockMvc.perform(put("/companies/" + companySaved.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(companyInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.companyName").value("alibaba"));

        //then
    }

    @Test
    void should_return_status_ok_when_delete_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "tencent", 1, emptyList());
        companyRepository.save(company);

        //when
        mockMvc.perform(delete("/companies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //then
    }
}
