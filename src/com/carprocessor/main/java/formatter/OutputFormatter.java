package com.carprocessor.main.java.formatter;

import com.carprocessor.main.java.model.Car;

import java.util.List;

public interface OutputFormatter {
    String format(List<Car> cars);
}
