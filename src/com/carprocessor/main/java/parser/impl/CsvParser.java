package com.carprocessor.main.java.parser.impl;

import com.carprocessor.main.java.model.Car;
import com.carprocessor.main.java.parser.FileParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.carprocessor.main.java.utils.Constant.DATE_FORMAT;
import static com.carprocessor.main.java.utils.Constant.UNKNOWN;

public class CsvParser implements FileParser {

    @Override
    public List<Car> parse(String filePath) throws Exception {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                line = line.replace("\"", "").trim();
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String brand = parts[0].trim();
                    LocalDate date = parseCsvDate(parts[1].trim());
                    Car car = new Car(brand, UNKNOWN, UNKNOWN, 0.0, date);
                    cars.add(car);
                }
            }
        }
        return cars;
    }


    public static LocalDate parseCsvDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            System.err.println("Failed to parse date: " + dateStr);
        }
        return null;
    }
}
