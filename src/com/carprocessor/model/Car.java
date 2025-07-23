package com.carprocessor.model;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.carprocessor.utils.Constant.USD;

public class Car {
    private String brand;
    private String type;
    private String model;
    private double priceUSD;

    private Map<String, Double> prices = new HashMap<>();
    private LocalDate releaseDate;

    public Car(String brand, String type, String model, double priceUSD, LocalDate releaseDate) {
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.priceUSD = priceUSD;
        this.releaseDate = releaseDate;
    }

    public String getBrand() {
        return brand;
    }

    public double getPriceUSD() {
        return priceUSD;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void addPrice(String currency, double price) {
        this.prices.put(currency, price);
    }

    public double getPrice(String currency) {
        if (USD.equals(currency)) {
            return priceUSD;
        }
        return prices.getOrDefault(currency, 0.0);
    }
}
