package com.tzs.antique.system.entity;

public class PieData {
    private int value;
    private String name;

    public PieData(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}