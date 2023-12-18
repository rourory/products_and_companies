package main.java.data_access_layer.entity;

import java.sql.Timestamp;

/**
 * Данный класс называют POJO. он необходим для объектного представления таблицы в БД.
 */
public class Product extends Entity {
    private String productName;
    private Double price;
    private Long categoryId;
    private Long companyId;
    private Timestamp createdAt;
    private Timestamp updatedAt;


    public Product() {
    }

    public Product(Long id, String productName, Double price, Long categoryId, Long companyId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.categoryId = categoryId;
        this.companyId = companyId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", companyId=" + companyId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
