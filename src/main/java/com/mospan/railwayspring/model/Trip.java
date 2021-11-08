package com.mospan.railwayspring.model;

import java.time.LocalDate;

public class Trip extends Entity{

    private Route route;
    private LocalDate departDate;
    private LocalDate arrivalDate;
    private int availablePlaces = 50;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public LocalDate getDepartDate() {
        return departDate;
    }

    public void setDepartDate(LocalDate departDate) {
        this.departDate = departDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }


}
