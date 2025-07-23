package com.carprocessor.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Car {
    private String brand;
    private String type;
    private String model;
    private double priceUSD;
    private Map<String, Double> prices = new HashMap<>();
    private LocalDate releaseDate;

    // Constructor
    public Car(String brand, String type, String model, double priceUSD, LocalDate releaseDate) {
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.priceUSD = priceUSD;
        this.releaseDate = releaseDate;
    }

    // Getters
    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public double getPriceUSD() {
        return priceUSD;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    // Method to add prices in other currencies
    public void addPrice(String currency, double price) {
        this.prices.put(currency, price);
    }

    // Get price for a specific currency
    public double getPrice(String currency) {
        // Use the price in USD if the currency is not in the map
        return prices.getOrDefault(currency, (currency.equals("USD")) ? priceUSD : 0.0);
    }

    public Map<String, Double> getPrices() {
        return prices;
    }
}
