package main.java.data_access_layer.repository;

import main.java.data_access_layer.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class CategoryRepository extends AbstractRepository<Category> {

    private final String TABLE_NAME = "categories";

    public List<Category> findAll() {
        return super.findAll(TABLE_NAME);
    }

    public Optional<Category> findOneById(Long id) {
        return super.findOneById(id, TABLE_NAME);
    }

    public Category save(Category entity) {
        Timestamp created = new Timestamp(System.currentTimeMillis());
        entity.setCreatedAt(created);
        entity.setUpdatedAt(created);
        return super.save(entity, TABLE_NAME);
    }

    public Category update(Category entity) {
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return super.update(entity, TABLE_NAME);
    }

    public Boolean removeById(Long id) {
        return super.removeById(id, TABLE_NAME);
    }

    @Override
    protected Category build(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getLong("id"),
                resultSet.getString("category_name"),
                resultSet.getTimestamp("created_at"),
                resultSet.getTimestamp("updated_at")
        );
    }
}
