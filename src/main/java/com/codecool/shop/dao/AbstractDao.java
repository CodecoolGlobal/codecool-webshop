package com.codecool.shop.dao;


import java.util.List;

public interface AbstractDao<T> {

    void add(T t);
    T find(int id);
    void remove(int id);
    List<T> getAll();
    <E> List<T> getBy(int id);



}
