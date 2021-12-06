package com.mospan.railwayspring.dao.interfaces;

import com.mospan.railwayspring.model.db.Detail;


public interface DetailDao {
    void insert(Detail detail);
    void update(Detail detail);
    Detail find(String email);
    Detail findById(long id);
    void delete(Detail detail);
}
