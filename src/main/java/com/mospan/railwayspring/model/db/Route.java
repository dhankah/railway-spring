package com.mospan.railwayspring.model.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;

@Entity
@Table(name = "route")
public class Route{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private double price;

    @ManyToOne
    @JoinColumn(name = "start_station_id")
    private Station startStation;
    @ManyToOne
    @JoinColumn(name = "end_station_id")
    private Station endStation;

    @OneToMany(mappedBy = "route")
    private Collection<Trip> trips;

    private LocalTime departTime;
    private long time;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public LocalTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalTime departTime) {
        this.departTime = departTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public LocalTime getArrivalTime() {
        return departTime.plus(Duration.ofSeconds(time));
    }

    private long[] timeUnits() {
        long t = time;
        long day = t / (24 * 3600);

        t = t % (24 * 3600);
        long hour = t / 3600;

        t %= 3600;
        long minute = t / 60 ;
        return new long[]{day, hour, minute};
    }


    public long getDay() {
        return timeUnits()[0];
    }

    public long getHour() {
        return timeUnits()[1];
    }

    public long getMinute() {
        return timeUnits()[2];
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

