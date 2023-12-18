package main.java.data_access_layer.repository;

import main.java.data_access_layer.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class CountryRepository extends AbstractRepository<Country> {

    private final String TABLE_NAME = "countries";

    public List<Country> findAll() {
        return super.findAll(TABLE_NAME);
    }


    public Optional<Country> findOneById(Long id) {
        return super.findOneById(id, TABLE_NAME);
    }

    public Country save(Country entity) {
        Timestamp created = new Timestamp(System.currentTimeMillis());
        entity.setCreatedAt(created);
        return super.save(entity, TABLE_NAME);
    }

    public Country update(Country entity) {
        return super.update(entity, TABLE_NAME);
    }

    public Boolean removeById(Long id) {
        return super.removeById(id, TABLE_NAME);
    }

    @Override
    protected Country build(ResultSet resultSet) throws SQLException {
        return new Country(
                resultSet.getLong("id"),
                resultSet.getString("country_name"),
                resultSet.getTimestamp("created_at")
        );
    }
}
