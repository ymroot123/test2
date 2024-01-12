package com.example.test;

import java.util.List;

public interface JdbcOperation<T, K> {
    void insert(T entity);
    void update(Student entity, String primaryKey);

    void delete(String primaryKey);

    List<T> queryAll();

    List<com.example.test.Student> queryWithPagination(int page, int pageSize);
    List<com.example.test.Student>searchByCondition(String sno, String name, String sex, String date, String phone);
}
