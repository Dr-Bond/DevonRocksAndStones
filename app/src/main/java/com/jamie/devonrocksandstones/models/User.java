package com.jamie.devonrocksandstones.models;

public class User
{

    private int id;
    private String email;
    private String firstName;
    private String surname;
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String county;
    private String postcode;
    private String accessToken;

    public User(int id, String email, String firstName, String surname, String addressLineOne, String addressLineTwo, String city, String county, String postcode, String accessToken) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
        this.accessToken = accessToken;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
