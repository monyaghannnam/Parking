package com.example.parking;

public class Word {
    String imgUrl;
    String day;
    String weather;
    String temp;

    public Word( String day, String weather, String temp) {
        this.imgUrl = imgUrl;
        this.day = day;
        this.weather = weather;
        this.temp = temp;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDay() {
        return day;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemp() {
        return temp;
    }
}