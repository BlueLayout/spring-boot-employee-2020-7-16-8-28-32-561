package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyResponse companyToCompanyResponse(Company company);

    Company companyResponseToCompany(CompanyResponse companyResponse);

    CompanyRequest companyToCompanyRequest(Company company);

    Company companyRequestToCompany(CompanyRequest companyRequest);
}
