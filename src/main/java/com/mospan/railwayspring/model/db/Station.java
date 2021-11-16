package com.mospan.railwayspring.model.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;

    @OneToMany(mappedBy = "startStation")
    private Collection<Route> routesForStart;

    @OneToMany(mappedBy = "endStation")
    private Collection<Route> routesForEnd;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<Route> getRoutesForStart() {
        return routesForStart;
    }

    public void setRoutesForStart(Collection<Route> routesForStart) {
        this.routesForStart = routesForStart;
    }

    public Collection<Route> getRoutesForEnd() {
        return routesForEnd;
    }

    public void setRoutesForEnd(Collection<Route> routesForEnd) {
        this.routesForEnd = routesForEnd;
    }
}
