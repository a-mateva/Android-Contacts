package com.example.contacts;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;
    private String description;
    private String category;

    public Contact(String name, String phone, String description, String category) {
        this.name = name;
        this.phoneNumber = phone;
        this.description = description;
        this.category = category;
    }

    public Contact() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }*/

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
