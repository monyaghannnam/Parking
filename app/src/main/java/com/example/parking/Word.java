package com.example.parking;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {
    private String name;
    private int free_slot;
    private double cor1,cor2;
    private String id;

    public Word() {
    }

    protected Word(Parcel in) {
        name = in.readString();
        free_slot = in.readInt();
        cor1 = in.readDouble();
        cor2 = in.readDouble();
        id = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFree_slot() {
        return free_slot;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(free_slot);
        dest.writeDouble(cor1);
        dest.writeDouble(cor2);
        dest.writeString(id);
    }
}
