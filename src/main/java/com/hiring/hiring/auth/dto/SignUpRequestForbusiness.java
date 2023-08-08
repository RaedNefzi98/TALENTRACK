package com.hiring.hiring.auth.dto;

import java.util.Date;

public class SignUpRequestForbusiness {
    @NotBlank
    @Pattern(regexp="^[A-Za-z][A-Za-z0-9]+$",message="companyName must not begin with a number nor contain special caracters")
    @Size(min = 3, max = 20)
    @NotEmpty
    private String companyName;

    @NotEmpty
    @NotBlank
    private String website;
    @NotEmpty
    private String nbEmployee;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @NotEmpty
    private String recrutementRole;

    @NotEmpty

    private String phone;
    @NotEmpty
    private String industry;
    @NotEmpty
    private String country;

    @NotEmpty
    private String address;


    private String logo;

    private boolean verified;

    @NotEmpty
    private String description;



    private Date creationDate;



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }




    public String getWebsite() {
        return website;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
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











    public SignUpRequestForbusiness() {
        super();
    }

    public SignUpRequestForbusiness(
            @NotBlank @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]+$", message = "displayName must not begin with a number nor contain special caracters") @Size(min = 3, max = 20) @NotEmpty String companyName,
            @NotEmpty String website, @NotEmpty String nbEmployee, @NotEmpty String firstName,
            @NotEmpty String lastName, @NotEmpty String recrutementRole, @NotEmpty String phone,
            @NotEmpty String industry, @NotEmpty String country, @NotEmpty String address, @NotEmpty String logo,
            @NotEmpty String description) {
        super();
        this.companyName = companyName;
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
    }





}

