package main.java.data_access_layer.entity;

public abstract class Entity {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
