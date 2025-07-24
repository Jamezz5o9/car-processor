package com.carprocessor.main.java.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.carprocessor.main.java.utils.Constant.USD;

public class Car {
    private String brand;
    private String type;
    private String model;
    private double priceUSD;
    private Map<String, Double> prices;
    private LocalDate releaseDate;

    public Car() {
        this.prices = new HashMap<>();
    }

    public Car(String brand, String type, String model, double priceUSD, LocalDate releaseDate) {
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.priceUSD = priceUSD;
        this.releaseDate = releaseDate;
        this.prices = new HashMap<>();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPriceUSD() {
        return priceUSD;
    }


    public Map<String, Double> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Double> prices) {
        this.prices = prices;
    }


    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public double getPrice(String currency) {
        if (USD.equals(currency)) {
            return priceUSD;
        }
        return prices.getOrDefault(currency, 0.0);
    }

    @Override
    public String toString() {
        return "Car{" + "brand='" + brand + '\'' + ", type='" + type + '\'' + ", model='" + model + '\'' + ", priceUSD=" + priceUSD + ", releaseDate=" + releaseDate + '}';
    }
}