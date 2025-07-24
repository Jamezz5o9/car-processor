package com.carprocessor.main.formatter;

import com.carprocessor.main.model.Car;

import java.util.List;

public interface OutputFormatter {
    String format(List<Car> cars);
}
