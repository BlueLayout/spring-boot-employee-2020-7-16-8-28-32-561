package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.IllegalUpdateCompanyException;
import com.thoughtworks.springbootemployee.Exception.NoSuchCompanyException;
import com.thoughtworks.springbootemployee.constant.ExceptionMessage;
import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyResponse> getCompanies() {
        return companyRepository.findAll().stream().map(CompanyMapper.INSTANCE::companyToCompanyResponse).collect(Collectors.toList());
    }

    public Page<CompanyResponse> getCompaniesPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page-1, pageSize)).map(CompanyMapper.INSTANCE::companyToCompanyResponse);
    }

    public CompanyResponse getCompany(int id) {
        Company company = companyRepository.findById(id).orElse(null);
        return CompanyMapper.INSTANCE.companyToCompanyResponse(company);
    }


    public List<Employee> getEmployees(int companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            throw new NoSuchCompanyException(ExceptionMessage.NO_SUCH_EMPLOYEE.getErrorMsg());
        }
        return company.getEmployees();
    }

    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        Company company = CompanyMapper.INSTANCE.companyRequestToCompany(companyRequest);
        Company companySave = companyRepository.save(company);
        return CompanyMapper.INSTANCE.companyToCompanyResponse(companySave);
    }

    public CompanyResponse updateCompany(int companyId, CompanyRequest companyRequest) {
        if (companyId != companyRequest.getId()) {
            throw new IllegalUpdateCompanyException(ExceptionMessage.ILLEGAL_UPDATE_COMPANY.getErrorMsg());
        }

        Company oldCompany = companyRepository.findById(companyId).orElse(null);
        if (oldCompany == null) {
            throw new NoSuchCompanyException(ExceptionMessage.NO_SUCH_COMPANY.getErrorMsg());
        }

        if (companyRequest.getCompanyName() != null) {
            oldCompany.setCompanyName(companyRequest.getCompanyName());
        }

        if (companyRequest.getEmployeeNumber() > 0) {
            oldCompany.setEmployeeNumber(companyRequest.getEmployeeNumber());
        }

        if (companyRequest.getEmployees().size() > 0) {
            oldCompany.setEmployees(companyRequest.getEmployees());
        }

        return CompanyMapper.INSTANCE.companyToCompanyResponse(companyRepository.save(oldCompany));
    }

    public void deleteCompany(int companyId) {
        Company oldCompany = companyRepository.findById(companyId).orElse(null);
        if (Objects.isNull(oldCompany)) {
            throw new NoSuchCompanyException(ExceptionMessage.NO_SUCH_COMPANY.getErrorMsg());
        }
        companyRepository.deleteById(companyId);
    }
}
