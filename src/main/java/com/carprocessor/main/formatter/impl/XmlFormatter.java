package com.carprocessor.main.formatter.impl;

import com.carprocessor.main.formatter.OutputFormatter;
import com.carprocessor.main.model.Car;
import java.util.List;
import java.util.Map;

public class XmlFormatter implements OutputFormatter {

    @Override
    public String format(List<Car> cars) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<cars>\n");

        for (Car car : cars) {
            sb.append("  <car");
            if (car.getBrand() != null) sb.append(" brand=\"").append(car.getBrand()).append("\"");
            sb.append(">\n");

            appendElement(sb, "type", car.getType());
            appendElement(sb, "model", car.getModel());

            sb.append("    <price currency=\"USD\">").append(String.format("%.2f", car.getPriceUSD())).append("</price>\n");

            if (!car.getPrices().isEmpty()) {
                sb.append("    <prices>\n");
                for (Map.Entry<String, Double> entry : car.getPrices().entrySet()) {
                    sb.append("      <price currency=\"").append(entry.getKey())
                            .append("\">").append(String.format("%.2f", entry.getValue()))
                            .append("</price>\n");
                }
                sb.append("    </prices>\n");
            }

            if (car.getReleaseDate() != null) appendElement(sb, "releaseDate", car.getReleaseDate().toString());
            sb.append("  </car>\n");
        }

        sb.append("</cars>");
        return sb.toString();
    }

    private void appendElement(StringBuilder sb, String tag, String value) {
        if (value != null) sb.append("    <").append(tag).append(">").append(value).append("</").append(tag).append(">\n");
    }
}