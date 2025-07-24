package com.carprocessor.main.java.formatter.impl;

import com.carprocessor.main.java.formatter.OutputFormatter;
import com.carprocessor.main.java.model.Car;
import java.util.List;

public class TableFormatter implements OutputFormatter {

    @Override
    public String format(List<Car> cars) {
        if (cars == null || cars.isEmpty()) {
            return "No cars to display.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-10s %-15s %-10s %-12s%n", "Brand", "Type", "Model", "Price(USD)", "Release Date"));
        sb.append("-".repeat(65)).append("\n");

        for (Car car : cars) {
            String brand = car.getBrand() != null ? car.getBrand() : "N/A";
            String type = car.getType() != null ? car.getType() : "N/A";
            String model = car.getModel() != null ? car.getModel() : "N/A";
            String price = String.format("$%.2f", car.getPriceUSD());
            String date = car.getReleaseDate() != null ? car.getReleaseDate().toString() : "N/A";
            sb.append(String.format("%-15s %-10s %-15s %-10s %-12s%n", brand, type, model, price, date));
        }
        return sb.toString();
    }
}