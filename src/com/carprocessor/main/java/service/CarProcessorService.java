package com.carprocessor.main.java.service;

import com.carprocessor.main.java.formatter.impl.JsonFormatter;
import com.carprocessor.main.java.formatter.impl.TableFormatter;
import com.carprocessor.main.java.formatter.impl.XmlFormatter;
import com.carprocessor.main.java.model.Car;
import com.carprocessor.main.java.formatter.OutputFormatter;
import com.carprocessor.main.java.parser.impl.CsvParser;
import com.carprocessor.main.java.parser.impl.XmlParser;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.carprocessor.main.java.utils.Constant.EUR;
import static com.carprocessor.main.java.utils.Constant.JPY;

public class CarProcessorService {

    private final XmlParser xmlParser;
    private final CsvParser csvParser;
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


    public List<Car> parseFiles(String csvFilePath, String xmlFilePath) {
        try {

            List<Car> csvCars = parseFile(csvFilePath, csvParser);
            if (csvCars == null || csvCars.isEmpty()) {
                System.err.println("Failed to parse CSV file");
                return null;
            }

            List<Car> xmlCars = parseFile(xmlFilePath, xmlParser);
            if (xmlCars == null || xmlCars.isEmpty()) {
                System.err.println("Failed to parse XML file");
                return null;
            }

            List<Car> mergedCars = new ArrayList<>();
            int minSize = Math.min(csvCars.size(), xmlCars.size());

            for (int i = 0; i < minSize; i++) {
                Car csvCar = csvCars.get(i);
                Car xmlCar = xmlCars.get(i);

                Car mergedCar = new Car(
                        csvCar.getBrand(),
                        xmlCar.getType(),
                        xmlCar.getModel(),
                        xmlCar.getPriceUSD(),
                        csvCar.getReleaseDate()
                );
                mergedCar.setPrices(xmlCar.getPrices());
                mergedCars.add(mergedCar);
            }

            return mergedCars;

        } catch (Exception e) {
            System.err.println("Error parsing files: " + e.getMessage());
            return null;
        }
    }

    private List<Car> parseFile(String filePath, Object parser) {
        try {
            String resourcePath = resolveResourcePath(filePath);
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

            if (resourceStream != null) {
                Path tempFile = Files.createTempFile("car-data", getFileExtension(filePath));
                Files.copy(resourceStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                resourceStream.close();

                List<Car> cars;
                if (parser instanceof CsvParser) {
                    cars = ((CsvParser) parser).parse(tempFile.toString());
                } else {
                    cars = ((XmlParser) parser).parse(tempFile.toString());
                }

                Files.deleteIfExists(tempFile);
                return cars;
            }

            File file = new File(filePath);
            if (!file.exists()) {
                file = new File(System.getProperty("user.dir"), filePath);
            }

            if (!file.exists()) {
                System.err.println("File not found: " + filePath);
                return null;
            }

            if (parser instanceof CsvParser) {
                return ((CsvParser) parser).parse(file.getAbsolutePath());
            } else {
                return ((XmlParser) parser).parse(file.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Error parsing file: " + e.getMessage());
            return null;
        }
    }

    private String resolveResourcePath(String filePath) {
        if (filePath.startsWith("src/com.carprocessor.main/resources/")) {
            return filePath.substring("src/com.carprocessor.main/resources/".length());
        }
        if (!filePath.contains("/") && !filePath.contains("\\")) {
            return filePath;
        }
        return Paths.get(filePath).getFileName().toString();
    }

    private String getFileExtension(String filePath) {
        int lastDot = filePath.lastIndexOf('.');
        return lastDot > 0 ? filePath.substring(lastDot) : "";
    }

    public List<Car> filterByBrandAndPrice(List<Car> cars, String brand, double maxPrice) {
        return cars.stream()
                .filter(car -> car.getBrand() != null && car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> car.getPriceUSD() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Car> filterByBrandAndReleaseDate(List<Car> cars, String brand, String dateStr) {
        LocalDate targetDate = parseDate(dateStr);
        if (targetDate == null) {
            System.err.println("Invalid date format. Use yyyy-MM-dd or yyyy,dd,MM");
            return new ArrayList<>();
        }

        return cars.stream()
                .filter(car -> car.getBrand() != null && car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> car.getReleaseDate() != null && car.getReleaseDate().equals(targetDate))
                .collect(Collectors.toList());
    }

    public List<Car> sortByReleaseYear(List<Car> cars) {
        return cars.stream()
                .filter(car -> car.getReleaseDate() != null)
                .sorted((c1, c2) -> c2.getReleaseDate().compareTo(c1.getReleaseDate()))
                .collect(Collectors.toList());
    }

    public List<Car> sortByPrice(List<Car> cars) {
        return cars.stream()
                .sorted((c1, c2) -> Double.compare(c2.getPriceUSD(), c1.getPriceUSD()))
                .collect(Collectors.toList());
    }

    public List<Car> sortByTypeAndCurrency(List<Car> cars) {
        List<Car> result = new ArrayList<>();


        List<Car> suvs = cars.stream()
                .filter(car -> "SUV".equalsIgnoreCase(car.getType()))
                .sorted((c1, c2) -> Double.compare(c2.getPrice(EUR), c1.getPrice(EUR)))
                .toList();


        List<Car> sedans = cars.stream()
                .filter(car -> "Sedan".equalsIgnoreCase(car.getType()))
                .sorted((c1, c2) -> Double.compare(c2.getPrice(JPY), c1.getPrice(JPY)))
                .toList();


        List<Car> trucks = cars.stream()
                .filter(car -> "Truck".equalsIgnoreCase(car.getType()))
                .sorted((c1, c2) -> Double.compare(c2.getPriceUSD(), c1.getPriceUSD()))
                .toList();

        result.addAll(suvs);
        result.addAll(sedans);
        result.addAll(trucks);

        return result;
    }

    public String formatOutput(List<Car> cars, String choice) {
        OutputFormatter formatter = switch (choice) {
            case "1" -> tableFormatter;
            case "2" -> xmlFormatter;
            case "3" -> jsonFormatter;
            default -> tableFormatter;
        };
        return formatter.format(cars);
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            try {
                String[] parts = dateStr.split(",");
                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0].trim());
                    int day = Integer.parseInt(parts[1].trim());
                    int month = Integer.parseInt(parts[2].trim());
                    return LocalDate.of(year, month, day);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}