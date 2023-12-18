package main.java.service_layer.service;

import main.java.data_access_layer.dto.CountryDTO;
import main.java.data_access_layer.entity.Country;
import main.java.data_access_layer.repository.CountryRepository;
import main.java.service_layer.mapper.CountryMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryService implements IService<CountryDTO, Long> {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService() {
        this.countryMapper = new CountryMapper();
        this.countryRepository = new CountryRepository();
    }


    @Override
    public List<CountryDTO> findAll() {
        List<Country> all = countryRepository.findAll();
        List<CountryDTO> dtos = new ArrayList<>();
        for (Country c : all) {
            dtos.add(countryMapper.map(c));
        }
        return dtos;
    }

    @Override
    public CountryDTO findOneById(Long id) {
        Optional<Country> country = countryRepository.findOneById(id);
        return country.map(countryMapper::map).orElse(null);
    }

    @Override
    public CountryDTO save(CountryDTO entity) {
        Country saved = countryRepository.save(countryMapper.remap(entity, null));
        return countryMapper.map(saved);
    }

    @Override
    public CountryDTO update(CountryDTO entity) {
        Optional<Country> country = countryRepository.findOneById(entity.getId());
        if (country.isPresent()) {
            Country updated = countryRepository.update(
                    countryMapper.remap(entity, country.get().getCreatedAt()));
            return countryMapper.map(updated);
        }
        return null;
    }

    @Override
    public Boolean removeById(Long id) {
        return countryRepository.removeById(id);
    }
}
