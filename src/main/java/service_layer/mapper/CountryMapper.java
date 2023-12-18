package main.java.service_layer.mapper;

import main.java.data_access_layer.dto.CountryDTO;
import main.java.data_access_layer.entity.Country;

import java.sql.Timestamp;

/**
 * Класс необжим для конвертации Entity в DTO наоборот.
 */
public class CountryMapper {

    public CountryDTO map(Country entity) {
        return new CountryDTO(entity.getId(), entity.getCountryName());
    }

    public Country remap(CountryDTO entity, Timestamp createdAt) {
        return new Country(entity.getId(),
                entity.getCountryName(),
                createdAt);
    }
}
