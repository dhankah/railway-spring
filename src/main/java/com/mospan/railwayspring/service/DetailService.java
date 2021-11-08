package com.mospan.railwayspring.service;

import com.mospan.railwayspring.dao.Dao;
import com.mospan.railwayspring.dao.DetailDao;
import com.mospan.railwayspring.model.Detail;

import java.util.Collection;

public class DetailService {
    Dao<Detail> dao = new DetailDao();

    public void insert(Detail detail) {
        dao.insert(detail);
    }
    public void update(Detail detail) {
        dao.update(detail);
    }
    public Detail find(String param) {
        return dao.find(param);
    }
    public Detail findById(long id) {
        return dao.findById(id);
    }
    public void delete(Detail detail) {
        dao.delete(detail);
    }
    public Collection<Detail> findAll() {
        return dao.findAll();
    }
}
