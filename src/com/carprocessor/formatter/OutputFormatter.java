package com.carprocessor.formatter;

import com.carprocessor.model.Car;

import java.util.List;

public interface OutputFormatter {
    String format(List<Car> cars);
}
