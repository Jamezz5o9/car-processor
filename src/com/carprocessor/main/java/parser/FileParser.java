package com.carprocessor.main.java.parser;

import com.carprocessor.main.java.model.Car;

import java.util.List;

public interface FileParser {
    List<Car> parse(String filePath) throws Exception;
}
