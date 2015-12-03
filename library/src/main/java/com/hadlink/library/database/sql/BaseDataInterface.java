package com.hadlink.library.database.sql;


import java.util.List;

/**
 * Created by Administrator on 2015/4/24.
 */
public interface BaseDataInterface<A> {

    public boolean save(A a);
    public boolean saveAll(List<A> list);

    public boolean delete(A a);
    public boolean deleteAll();


}
