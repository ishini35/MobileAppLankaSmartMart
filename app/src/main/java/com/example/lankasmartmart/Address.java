package com.example.lankasmartmart;

public class Address {
    private int id;
    private int userId;
    private String type; // "Home" or "Office"
    private String addressLine;
    private String city;

    public Address(int id, int userId, String type, String addressLine, String city) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.addressLine = addressLine;
        this.city = city;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getFullAddress() {
        return addressLine + ", " + city;
    }
}
