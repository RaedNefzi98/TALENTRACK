package com.hiring.hiring.entity;

import java.util.Date;

public class Business {

    private String idBusiness;
    private String companyName;
    private String email;
    private String password;

    private boolean active;

    private boolean enabled;

    private String website;

    private String nbEmployee;

    private String firstName;

    private String lastName;


    private String recrutementRole;


    private String phone;


    private String industry;


    private String country;


    private String address;


    private String logo;



    private String description;



    private Date creationDate;

    public Business() {
    }


    public Business(String companyName,String email, String password, boolean active,boolean enabled,  String website, String nbEmployee, String firstName,
                    String lastName, String recrutementRole, String phone,
                    String industry, String country, String address, String logo, String description, Date creationDate) {
        super();

        this.companyName = companyName;
        this.email = email;
        this.password = password;
        this.active = active;
        this.enabled = enabled;
        this.website = website;
        this.nbEmployee = nbEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
        this.recrutementRole = recrutementRole;
        this.phone = phone;
        this.industry = industry;
        this.country = country;
        this.address = address;
        this.logo = logo;
        this.description = description;
        this.creationDate = creationDate;
    }

    public String getIdBusiness() {
        return idBusiness;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNbEmployee() {
        return nbEmployee;
    }

    public void setNbEmployee(String nbEmployee) {
        this.nbEmployee = nbEmployee;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getRecrutementRole() {
        return recrutementRole;
    }

    public void setRecrutementRole(String recrutementRole) {
        this.recrutementRole = recrutementRole;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }





}

