package com.example.parking;

public class Word {
    String name;
    int free_slot;
    double cor1,cor2;
    String id;


    public Word(String name, int free_slot ) {
        this.name = name;
        this.free_slot = free_slot;

    }

    public String getName() {
        return name;
    }

    public int getFree_slot() {
        return free_slot;
    }

    public Word() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFree_slot(int free_slot) {
        this.free_slot = free_slot;
    }

    public double getCor1() {
        return cor1;
    }

    public void setCor1(double cor1) {
        this.cor1 = cor1;
    }

    public double getCor2() {
        return cor2;
    }

    public void setCor2(double cor2) {
        this.cor2 = cor2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
