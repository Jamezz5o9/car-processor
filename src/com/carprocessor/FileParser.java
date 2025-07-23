package com.carprocessor;

import com.carprocessor.model.Car;

import java.util.List;

public interface FileParser {
    List<Car> parse(String filePath) throws Exception;
}
