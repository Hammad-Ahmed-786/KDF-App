package com.example.kdf.models;

public class SubMenuModel {
    int id, menuId, status;
    String name, description, image, price;

    public SubMenuModel(int id, int menuId, int status, String name, String description, String image, String price) {
        this.id = id;
        this.menuId = menuId;
        this.status = status;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public SubMenuModel(int id, String name, String description, String image, String price, int status) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public SubMenuModel(String name, String description, String image, String price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
