package com.mospan.railwayspring.model.db;

import com.mospan.railwayspring.model.Role;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id")
    private Detail details;
    private String login;
    private String password;
    private double balance;

    @OneToMany(mappedBy = "user")
    private Collection<Ticket> tickets;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
