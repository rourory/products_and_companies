package main.java.service_layer.mapper;

import main.java.data_access_layer.dto.CategoryDTO;
import main.java.data_access_layer.dto.CompanyDTO;
import main.java.data_access_layer.dto.ProductDTO;
import main.java.data_access_layer.entity.Product;

import java.sql.Timestamp;

/**
 * Класс необжим для конвертации Entity в DTO наоборот.
 */
public class ProductMapper {
    public ProductDTO map(Product entity, CompanyDTO company, CategoryDTO category) {
        return new ProductDTO(entity.getId(),
                entity.getProductName(),
                entity.getPrice(),
                company,
                category);
    }

    public Product remap(ProductDTO entity, Timestamp createdAt, Timestamp updatedAt) {
        return new Product(entity.getId(),
                entity.getProductName(),
                entity.getPrice(),
                entity.getCategory().getId(),
                entity.getCompany().getId(),
                createdAt,
                updatedAt);
    }
}
