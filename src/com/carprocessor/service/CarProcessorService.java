package com.carprocessor.service;

import com.carprocessor.formatter.impl.JsonFormatter;
import com.carprocessor.formatter.impl.TableFormatter;
import com.carprocessor.formatter.impl.XmlFormatter;
import com.carprocessor.model.Car;
import com.carprocessor.formatter.OutputFormatter;
import com.carprocessor.parser.impl.CsvParser;
import com.carprocessor.parser.FileParser;
import com.carprocessor.parser.impl.XmlParser;


import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CarProcessorService {

    private final FileParser xmlParser;
    private final FileParser csvParser;
    private final OutputFormatter tableFormatter;
    private final OutputFormatter xmlFormatter;
    private final OutputFormatter jsonFormatter;

    public CarProcessorService() {
        this.xmlParser = new XmlParser();
        this.csvParser = new CsvParser();
        this.tableFormatter = new TableFormatter();
        this.xmlFormatter = new XmlFormatter();
        this.jsonFormatter = new JsonFormatter();
    }

    public List<Car> parseFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("File not found: " + filePath);
                return null;
            }
            FileParser parser = filePath.toLowerCase().endsWith(".xml") ? xmlParser : csvParser;
            return parser.parse(filePath);
        } catch (Exception e) {
            System.err.println("Error parsing file: " + e.getMessage());
            return null;
        }
    }

    public List<Car> filterByBrandAndPrice(List<Car> cars, String brand, double maxPrice) {
        return cars.stream()
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> car.getPriceUSD() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Car> filterByBrandAndReleaseDate(List<Car> cars, String brand, String dateStr) {
        LocalDate targetDate = parseDate(dateStr);
        return cars.stream()
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> car.getReleaseDate() != null)
                .filter(car -> car.getReleaseDate().equals(targetDate))
                .collect(Collectors.toList());
    }

    public List<Car> sortByReleaseYear(List<Car> cars) {
        return cars.stream()
                .sorted((c1, c2) -> c2.getReleaseDate().compareTo(c1.getReleaseDate()))
                .collect(Collectors.toList());
    }

    public List<Car> sortByPrice(List<Car> cars) {
        return cars.stream()
                .sorted((c1, c2) -> Double.compare(c2.getPriceUSD(), c1.getPriceUSD()))
                .collect(Collectors.toList());
    }

    public List<Car> sortByTypeAndCurrency(List<Car> cars) {
        return cars.stream()
                .sorted((c1, c2) -> Double.compare(c2.getPriceUSD(), c1.getPriceUSD()))
                .collect(Collectors.toList());
    }

    public String formatOutput(List<Car> cars, String choice) {
        OutputFormatter formatter = switch (choice) {
            case "1" -> xmlFormatter;
            case "2" -> jsonFormatter;
            default -> tableFormatter;
        };
        return formatter.format(cars);
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            String[] parts = dateStr.split(",");
            if (parts.length == 3) {
                int year = Integer.parseInt(parts[0].trim());
                int day = Integer.parseInt(parts[1].trim());
                int month = Integer.parseInt(parts[2].trim());
                return LocalDate.of(year, month, day);
            }
        }
        return null;
    }
}

