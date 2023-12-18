package main.java.data_access_layer.dto;

/**
 * Класс DTO необходим для представления данных в необходимом нам формате.
 * Мы ведь не хотим отдавать пользователю такие поля, как id, created_at, updated_at и прочее.
 */
public class CompanyDTO extends DTO {
    private String companyName;
    private CountryDTO country;

    public CompanyDTO() {
    }

    public CompanyDTO(Long id, String companyName, CountryDTO country) {
        this.id = id;
        this.companyName = companyName;
        this.country = country;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return companyName + " (" + country.getCountryName() + ")";
    }
}
