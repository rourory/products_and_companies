package main.java.data_access_layer.dto;

/**
 * Класс DTO необходим для представления данных в необходимом нам формате.
 * Мы ведь не хотим отдавать пользователю такие поля, как id, created_at, updated_at и прочее.
 */
public class ProductDTO extends DTO {
    private String productName;
    private Double price;
    private CompanyDTO company;
    private CategoryDTO category;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String productName, Double price, CompanyDTO company, CategoryDTO category) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.company = company;
        this.category = category;
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

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "id=" + id + ", productName='" + productName + '\'' + ", price=" + price + ", company=" + company.getId() + ", category=" + category.getId() + '}';
    }
}
