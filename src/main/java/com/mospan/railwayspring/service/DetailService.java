package com.mospan.railwayspring.service;

import com.mospan.railwayspring.dao.implementation.DetailDaoImpl;
import com.mospan.railwayspring.dao.interfaces.DetailDao;
import com.mospan.railwayspring.model.db.Detail;


public class DetailService {
    DetailDao dao = new DetailDaoImpl();

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
}
