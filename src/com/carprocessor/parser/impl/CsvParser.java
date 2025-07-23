package com.carprocessor.parser.impl;

import com.carprocessor.model.Car;
import com.carprocessor.parser.FileParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvParser implements FileParser {

    @Override
    public List<Car> parse(String filePath) throws Exception {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String brand = parts[0].trim();
                    String releaseDate = parts[1].trim();
                    LocalDate date = LocalDate.parse(releaseDate);
                    cars.add(new Car(brand, "Unknown", "Unknown", 0.0, date));
                }
            }
        }
        return cars;
    }
}

