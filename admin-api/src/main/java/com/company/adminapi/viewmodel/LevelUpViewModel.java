package com.company.adminapi.viewmodel;

import com.company.adminapi.models.Customer;

import java.time.LocalDate;
import java.util.Objects;

public class LevelUpViewModel {

    private int levelUpId;
    private Customer customer;
    private LocalDate memberDate;
    private int points;



    public int getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(int levelUpId) {
        this.levelUpId = levelUpId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUpViewModel that = (LevelUpViewModel) o;
        return getLevelUpId() == that.getLevelUpId() &&
                getPoints() == that.getPoints() &&
                Objects.equals(getCustomer(), that.getCustomer()) &&
                Objects.equals(getMemberDate(), that.getMemberDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevelUpId(), getCustomer(), getMemberDate(), getPoints());
    }
}
