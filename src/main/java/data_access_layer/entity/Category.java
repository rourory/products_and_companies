package main.java.data_access_layer.entity;

import java.sql.Timestamp;

/**
 * Данный класс называют POJO. он необходим для объектного представления таблицы в БД.
 */
public class Category extends Entity {
    private String categoryName;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Category() {
    }

    public Category(Long id, String categoryName, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
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
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
