package main.java.service_layer.mapper;

import main.java.data_access_layer.dto.CategoryDTO;
import main.java.data_access_layer.entity.Category;

import java.sql.Timestamp;

/**
 * Класс необжим для конвертации Entity в DTO наоборот.
 */
public class CategoryMapper {
    public CategoryDTO map(Category entity) {
        return new CategoryDTO(entity.getId(), entity.getCategoryName());
    }

    public Category remap(CategoryDTO entity, Timestamp createdAt, Timestamp updatedAt) {
        return new Category(entity.getId(),
                entity.getCategoryName(),
                createdAt,
                updatedAt);
    }
}
