package main.java.data_access_layer.dto;

/**
 * Класс DTO необходим для представления данных в необходимом нам формате.
 * Мы ведь не хотим отдавать пользователю такие поля, как id, created_at, updated_at и прочее.
 */
public class CountryDTO extends DTO {
    private String countryName;

    public CountryDTO(Long id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
