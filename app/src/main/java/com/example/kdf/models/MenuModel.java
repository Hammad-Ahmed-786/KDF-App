package com.example.kdf.models;

public class MenuModel {
    int id, status;
    String name, description, image;

    public MenuModel(int id, String name, String description, String image, int status) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public MenuModel(String name, String description, String image, int status) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public MenuModel(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
