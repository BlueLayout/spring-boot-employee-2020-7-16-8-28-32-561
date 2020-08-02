package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping(params = {"page", "pageSize"})
    public Page<CompanyResponse> getCompaniesInPage(@RequestParam(value = "page") Integer page,
                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        return companyService.getCompaniesPage(page, pageSize);
    }

    @GetMapping
    public List<CompanyResponse> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompany(@PathVariable int id) {
        return companyService.getCompany(id);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployeeFromCompany(@PathVariable int id) {
        return companyService.getCompany(id).getEmployees()
                .stream().map(EmployeeMapper.INSTANCE::employeeToEmployeeResponse).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.createCompany(companyRequest);
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompany(@PathVariable("id") int id, @RequestBody CompanyRequest companyRequest) {
        return companyService.updateCompany(id, companyRequest);
    }

    @DeleteMapping("{id}")
    public void deleteCompany(@PathVariable("id") int id) {
        companyService.deleteCompany(id);
    }

}
