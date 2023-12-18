package main.java.data_access_layer.repository;

import main.java.data_access_layer.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class ProductRepository extends AbstractRepository<Product> {

    private final String TABLE_NAME = "products";

    public List<Product> findAll() {
        return super.findAll(TABLE_NAME);
    }

    public List<Product> findAllByParameterID(Long companyId, String parameter){
        return super.findAllWhereParameterID(parameter, companyId, TABLE_NAME);
    }

    public Optional<Product> findOneById(Long id) {
        return super.findOneById(id, TABLE_NAME);
    }

    public Product save(Product entity) {
        Timestamp created = new Timestamp(System.currentTimeMillis());
        entity.setCreatedAt(created);
        entity.setUpdatedAt(created);
        return super.save(entity, TABLE_NAME);
    }

    public Product update(Product entity) {
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return super.update(entity, TABLE_NAME);
    }

    public Boolean removeById(Long id) {
        return super.removeById(id, TABLE_NAME);
    }

    @Override
    protected Product build(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("product_name"),
                resultSet.getDouble("price"),
                resultSet.getLong("category_id"),
                resultSet.getLong("company_id"),
                resultSet.getTimestamp("created_at"),
                resultSet.getTimestamp("updated_at")
        );
    }
}
