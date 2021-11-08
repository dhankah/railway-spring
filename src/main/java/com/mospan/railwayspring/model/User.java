package com.mospan.railwayspring.model;

public class User extends Entity{

    private Role role;
    private Detail details;
    private String login;
    private String password;
    private double balance;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Detail getDetails() {
        return details;
    }

    public void setDetails(Detail details) {
        this.details = details;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    public boolean getIsAdmin() {
        return isAdmin();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
