package com.example.test;

import javafx.scene.control.TextField;

import java.time.LocalDate;

public class Student {
    public String sno;
    public String name;
    public String date;
    public String sex;
    public String phone;


    public Student() {
    }

    public Student(String sno, String name, String date, String sex, String phone) {
        this.sno = sno;
        this.name = name;
        this.date = date;
        this.sex = sex;
        this.phone = phone;
    }

    /**
     * 获取
     * @return sno
     */
    public String getSno() {
        return sno;
    }

    /**
     * 设置
     * @param sno
     */
    public void setSno(String sno) {
        this.sno = sno;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "Student{sno = " + sno + ", name = " + name + ", date = " + date + ", sex = " + sex + ", phone = " + phone + "}";
    }
}