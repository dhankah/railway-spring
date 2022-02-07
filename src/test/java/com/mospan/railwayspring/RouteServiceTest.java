package com.mospan.railwayspring;

import com.mospan.railwayspring.dao.implementation.RouteDaoImpl;
import com.mospan.railwayspring.model.db.Route;
import com.mospan.railwayspring.service.RouteService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RouteServiceTest {

    static RouteService service;
    static Route route;

    @BeforeClass
    public static void setUp() {
        service = new RouteService();
        service.dao = Mockito.mock(RouteDaoImpl.class);
        route = Mockito.mock(Route.class);
    }

    @Test
    public void insertShouldCallDao() {
        service.insert(route);
        Mockito.verify(service.dao).insert(route);
    }

    @Test
    public void updateShouldCallDao() {
        service.update(route);
        Mockito.verify(service.dao).update(route);
    }

    @Test
    public void deleteShouldCallDao() {
        service.delete(route);
        Mockito.verify(service.dao).delete(route);
    }

    @Test
    public void findByIdShouldReturnARoute() {
        Mockito.when(service.dao.findById(Mockito.anyLong())).thenReturn(route);
        Route result = service.findById(1);
        Assert.assertEquals(result, route);
    }

    @Test
    public void findByAllShouldReturnACollection() {
        Collection<Route> routes = new ArrayList<Route>(Collections.singletonList(route));
        Mockito.when(service.dao.findAll()).thenReturn(routes);
        Collection<Route> result = service.findAll();
        Assert.assertEquals(result, routes);
    }

}
