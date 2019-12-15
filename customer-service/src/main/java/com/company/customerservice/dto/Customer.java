package com.company.customerservice.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Table(name = "customer")
//@Proxy(lazy = false)
public class Customer {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    @NotEmpty(message="")
    private String firstName;
    @NotEmpty(message="")
    private String lastName;
    @NotEmpty(message="")
    private String street;
    @NotEmpty(message="")
    private String city;
    @NotEmpty(message="")
    private String zip;
    @NotEmpty(message="")
    private String email;
    @NotEmpty(message="")
    private String phone;



    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return getCustomerId() == customer.getCustomerId() &&
                Objects.equals(getFirstName(), customer.getFirstName()) &&
                Objects.equals(getLastName(), customer.getLastName()) &&
                Objects.equals(getStreet(), customer.getStreet()) &&
                Objects.equals(getCity(), customer.getCity()) &&
                Objects.equals(getZip(), customer.getZip()) &&
                Objects.equals(getEmail(), customer.getEmail()) &&
                Objects.equals(getPhone(), customer.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getFirstName(), getLastName(), getStreet(), getCity(), getZip(), getEmail(), getPhone());
    }
}
