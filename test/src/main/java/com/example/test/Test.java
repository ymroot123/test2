package com.example.test;

public class Test {
    public static void main(String[] args) {
        com.example.test.StudentDaoImpl studentDao;
        studentDao=new com.example.test.StudentDaoImpl();
        System.out.println(studentDao.searchByCondition("S0", "", "", "", ""));
    }
}
