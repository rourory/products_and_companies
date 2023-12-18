package main.java.data_access_layer.dto;

/**
 * Нет необходимости дублировать этот код в каждом DTO классе, поэтому существует этот класс.
 */
public class DTO {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
