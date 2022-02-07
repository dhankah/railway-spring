package com.mospan.railwayspring;

import com.mospan.railwayspring.dao.implementation.StationDaoImpl;
import com.mospan.railwayspring.dao.interfaces.StationDao;
import com.mospan.railwayspring.model.db.Station;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StationDaoTest {
    static StationDao stationDao = new StationDaoImpl();

    @BeforeClass
    public static void setUp() {
        Station station = new Station();
        station.setName("Kyiv");
        stationDao.insert(station);
    }

    @Test
    public void insertionShouldSaveStationToDB() {
        Station station = new Station();
        station.setName("Odesa");
        stationDao.insert(station);

        Station s = stationDao.find("Odesa");

        Assert.assertNotNull(s);
    }

    @Test
    public void searchByIdShouldReturnStationIfIdISPresentInDB() {
        Station station = stationDao.findById(1);
        Assert.assertNotNull(station);
    }

    @Test
    public void searchByIdShouldReturnNullIfIdIsNotPresentInDB() {
        Station station = stationDao.findById(90);
        Assert.assertNull(station);
    }

    @Test
    public void updateShouldSaveChanges() {
        Station station = new Station();
        station.setName("Odesa");
        stationDao.insert(station);
        station = stationDao.find("Odesa");
        station.setName("Kharkiv");
        stationDao.update(station);
        Station s = stationDao.find("Kharkiv");
        Assert.assertNotNull(s);
    }

    @Test
    public void searchByNameShouldReturnStationIfPresentInDB() {
        Station station = stationDao.find("Kyiv");
        Assert.assertNotNull(station);
    }

    @Test
    public void searchByNameShouldReturnNullIfIdIsNotPresentInDB() {
        Station station = stationDao.find("New York");
        Assert.assertNull(station);
    }

    @Test
    public void shouldDeleteStationIfPresentInDB() {
        Station station = stationDao.find("Kyiv");
        stationDao.delete(station);
        Station s = stationDao.find("Kyiv");
        Assert.assertNull(s);
    }

}
