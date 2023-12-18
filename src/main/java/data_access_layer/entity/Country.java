package main.java.data_access_layer.entity;

import java.sql.Timestamp;

/**
 * Данный класс называют POJO. он необходим для объектного представления таблицы в БД.
 */
public class Country extends Entity {
    private String countryName;
    private Timestamp createdAt;

    public Country(Long id, String countryName, Timestamp createdAt) {
        this.id = id;
        this.countryName = countryName;
        this.createdAt = createdAt;
    }

    public Country() {
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Country{" + "id=" + id + ", countryName='" + countryName + '\'' + ", createdAt=" + createdAt + '}';
    }
}
