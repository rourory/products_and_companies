package main.java.service_layer.service;

import main.java.data_access_layer.dto.CompanyDTO;
import main.java.data_access_layer.dto.CountryDTO;
import main.java.data_access_layer.entity.Company;
import main.java.data_access_layer.entity.Country;
import main.java.data_access_layer.repository.CompanyRepository;
import main.java.data_access_layer.repository.CountryRepository;
import main.java.service_layer.mapper.CompanyMapper;
import main.java.service_layer.mapper.CountryMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyService implements IService<CompanyDTO, Long> {
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final CompanyMapper companyMapper;

    public CompanyService() {
        this.countryRepository = new CountryRepository();
        this.companyRepository = new CompanyRepository();
        this.companyMapper = new CompanyMapper();
        this.countryMapper = new CountryMapper();
    }

    @Override
    public List<CompanyDTO> findAll() {
        List<Company> all = companyRepository.findAll();
        List<CompanyDTO> dtos = new ArrayList<>();
        for (Company c : all) {
            CountryDTO countryDTO = null;
            Optional<Country> country = countryRepository.findOneById(c.getCountryId());
            if (country.isPresent()) {
                countryDTO = countryMapper.map(country.get());
            }
            dtos.add(companyMapper.map(c, countryDTO));
        }
        return dtos;
    }

    @Override
    public CompanyDTO findOneById(Long id) {
        Optional<Company> company = companyRepository.findOneById(id);
        if (company.isPresent()) {
            CountryDTO countryDTO = null;
            Optional<Country> country = countryRepository.findOneById(company.get().getCountryId());
            if (country.isPresent()) {
                countryDTO = countryMapper.map(country.get());
            }
            return companyMapper.map(company.get(), countryDTO);
        }
        return null;
    }

    @Override
    public CompanyDTO save(CompanyDTO entity) {
        Timestamp created = new Timestamp(System.currentTimeMillis());
        Company saved = companyRepository.save(companyMapper.remap(entity, created, created));
        Optional<Country> oneById = countryRepository.findOneById(saved.getCountryId());
        CountryDTO countryDTO = oneById.map(countryMapper::map).orElse(null);
        return companyMapper.map(saved, countryDTO);
    }

    @Override
    public CompanyDTO update(CompanyDTO entity) {
        Optional<Company> company = companyRepository.findOneById(entity.getId());
        if (company.isPresent()) {
            Company updated = companyRepository.update(companyMapper.remap(entity, company.get().getCreatedAt(), null));
            Optional<Country> oneById = countryRepository.findOneById(updated.getCountryId());
            CountryDTO countryDTO = oneById.map(countryMapper::map).orElse(null);
            return companyMapper.map(updated, countryDTO);
        }
        return entity;
    }

    @Override
    public Boolean removeById(Long id) {
        return companyRepository.removeById(id);
    }
}
