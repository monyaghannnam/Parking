package com.example.parking;

public class Word {
    String name;
    int free_slot;


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
}