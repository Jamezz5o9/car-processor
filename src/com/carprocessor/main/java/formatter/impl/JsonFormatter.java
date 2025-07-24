package com.carprocessor.main.java.formatter.impl;

import com.carprocessor.main.java.formatter.OutputFormatter;
import com.carprocessor.main.java.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter implements OutputFormatter {

    private final ObjectMapper objectMapper;

    public JsonFormatter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public String format(List<Car> cars) {
        try {
            Map<String, List<Car>> result = new HashMap<>();
            result.put("cars", cars);
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return manualFormat(cars);
        }
    }

    private String manualFormat(List<Car> cars) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"cars\": [\n");

        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            sb.append("    {\n");
            if (car.getBrand() != null) sb.append("      \"brand\": \"").append(car.getBrand()).append("\",\n");
            if (car.getType() != null) sb.append("      \"type\": \"").append(car.getType()).append("\",\n");
            if (car.getModel() != null) sb.append("      \"model\": \"").append(car.getModel()).append("\",\n");
            sb.append("      \"priceUSD\": ").append(car.getPriceUSD()).append(",\n");
            if (car.getReleaseDate() != null) sb.append("      \"releaseDate\": \"").append(car.getReleaseDate()).append("\"\n");
            sb.append("    }");
            if (i < cars.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n}");
        return sb.toString();
    }
}