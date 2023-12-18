package main.java.data_access_layer.entity;

import java.sql.Timestamp;

/**
 * Данный класс называют POJO. он необходим для объектного представления таблицы в БД.
 */
public class Company extends Entity {
    private String companyName;
    private Long countryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Company() {
    }

    public Company(Long id, String companyName, Long countryId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.companyName = companyName;
        this.countryId = countryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCountryId() {
        return this.countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
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
        return "Company{" + "id=" + id + ", companyName='" + companyName + '\'' + ", countryId=" + countryId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
