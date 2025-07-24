package com.carprocessor.main.parser;

import com.carprocessor.main.model.Car;

import java.util.List;

public interface FileParser {
    List<Car> parse(String filePath) throws Exception;
}
