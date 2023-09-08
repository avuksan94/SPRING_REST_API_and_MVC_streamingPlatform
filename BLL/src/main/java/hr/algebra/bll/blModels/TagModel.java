package hr.algebra.bll.blModels;

import jakarta.validation.constraints.NotEmpty;

public class TagModel {
    @NotEmpty(message = "Name is required")
    private String name;

    public TagModel() {
    }

    public TagModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagModel{" +
                ", name='" + name + '\'' +
                '}';
    }
}
