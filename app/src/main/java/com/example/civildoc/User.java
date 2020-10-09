package com.example.civildoc;


public class User {
    public String name, email,designation, phone,company,adderss;

    public User() {
    }

    public User(String name, String email, String designation, String phone, String company, String adderss) {
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.phone = phone;
        this.company = company;
        this.adderss = adderss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAdderss() {
        return adderss;
    }

    public void setAdderss(String adderss) {
        this.adderss = adderss;
    }
}
