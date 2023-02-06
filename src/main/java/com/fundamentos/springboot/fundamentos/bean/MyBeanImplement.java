package com.fundamentos.springboot.fundamentos.bean;

public class MyBeanImplement implements MyBean {


    @Override
    public void print() {
        System.out.println("hola desde mi implementacion propia del bean");

    }
}
