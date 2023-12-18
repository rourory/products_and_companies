package main.java.data_access_layer.repository;

import main.java.data_access_layer.entity.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class CompanyRepository extends AbstractRepository<Company> {

    private final String TABLE_NAME = "companies";

    public List<Company> findAll() {
        return super.findAll(TABLE_NAME);
    }

    public Optional<Company> findOneById(Long id) {
        return super.findOneById(id, TABLE_NAME);
    }

    public Company save(Company entity) {
        return super.save(entity, TABLE_NAME);
    }

    public Company update(Company entity) {
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return super.update(entity, TABLE_NAME);
    }

    public Boolean removeById(Long id) {
        return super.removeById(id, TABLE_NAME);
    }

    @Override
    protected Company build(ResultSet resultSet) throws SQLException {
        return new Company(
                resultSet.getLong("id"),
                resultSet.getString("company_name"),
                resultSet.getLong("country_id"),
                resultSet.getTimestamp("created_at"),
                resultSet.getTimestamp("updated_at")
        );
    }
}
