package com.carprocessor.main;

import com.carprocessor.main.model.Car;
import com.carprocessor.main.service.CarProcessorService;

import java.util.List;
import java.util.Scanner;

public class CarProcessorRunner {

    private final CarProcessorService carProcessorService;
    private final Scanner scanner = new Scanner(System.in);

    public CarProcessorRunner(CarProcessorService carProcessorService) {
        this.carProcessorService = carProcessorService;
    }

    public void run() {
        System.out.println("=== Car Data Processor ===");
        System.out.println("This application requires both CSV and XML files to process car data.");

        String csvPath = getUserInput("\nEnter CSV file path (contains Brand and ReleaseDate): ");

        String xmlPath = getUserInput("Enter XML file path (contains car details): ");

        List<Car> cars = carProcessorService.parseFiles(csvPath, xmlPath);

        if (cars == null || cars.isEmpty()) {
            System.err.println("Failed to process car data from files.");
            return;
        }

        System.out.println("\nSuccessfully loaded " + cars.size() + " cars.");

        cars = applyFilters(cars);

        cars = applySorting(cars);

        outputResults(cars);

        scanner.close();
    }

    private List<Car> applyFilters(List<Car> cars) {
        String choice = getUserInput("\nFilter Options:\n1. Filter by Brand and Price\n2. Filter by Brand and Release Date\n3. No filter\nChoose option (1-3): ");

        return switch (choice) {
            case "1" -> {
                String brand = getUserInput("Enter brand: ");
                double maxPrice = Double.parseDouble(getUserInput("Enter maximum price (USD): "));
                yield carProcessorService.filterByBrandAndPrice(cars, brand, maxPrice);
            }
            case "2" -> {
                String brand = getUserInput("Enter brand: ");
                String dateStr = getUserInput("Enter release date (yyyy-MM-dd or yyyy,dd,MM): ");
                yield carProcessorService.filterByBrandAndReleaseDate(cars, brand, dateStr);
            }
            default -> cars;
        };
    }

    private List<Car> applySorting(List<Car> cars) {
        String choice = getUserInput("\nSorting Options:\n1. Sort by Release Year (latest first)\n2. Sort by Price (highest first)\n3. Sort by Type and Currency\n4. No sorting\nChoose option (1-4): ");

        return switch (choice) {
            case "1" -> carProcessorService.sortByReleaseYear(cars);
            case "2" -> carProcessorService.sortByPrice(cars);
            case "3" -> carProcessorService.sortByTypeAndCurrency(cars);
            default -> cars;
        };
    }

    private void outputResults(List<Car> cars) {
        String choice = getUserInput("\nOutput Format:\n1. Table\n2. XML\n3. JSON\nChoose option (1-3): ");
        String output = carProcessorService.formatOutput(cars, choice);
        System.out.println("\n=== Results ===\n" + output);
    }

    private String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}