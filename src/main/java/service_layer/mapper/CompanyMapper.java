package main.java.service_layer.mapper;

import main.java.data_access_layer.dto.CompanyDTO;
import main.java.data_access_layer.dto.CountryDTO;
import main.java.data_access_layer.entity.Company;

import java.sql.Timestamp;

/**
 * Класс необжим для конвертации Entity в DTO наоборот.
 */
public class CompanyMapper {

    public CompanyDTO map(Company entity, CountryDTO country) {
        return new CompanyDTO(entity.getId(),
                entity.getCompanyName(),
                country);
    }

    public Company remap(CompanyDTO entity, Timestamp createdAt, Timestamp updatedAt) {
        return new Company(entity.getId(),
                entity.getCompanyName(),
                entity.getCountry().getId(),
                createdAt, updatedAt);
    }
}
