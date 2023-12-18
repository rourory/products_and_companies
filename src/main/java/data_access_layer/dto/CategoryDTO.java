package main.java.data_access_layer.dto;

/**
 * Класс DTO необходим для представления данных в необходимом нам формате.
 * Мы ведь не хотим отдавать пользователю такие поля, как id, created_at, updated_at и прочее.
 */
public class CategoryDTO extends DTO {
    private String categoryName;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }

}
