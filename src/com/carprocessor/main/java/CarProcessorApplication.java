package com.carprocessor.main.java;

import com.carprocessor.main.java.service.CarProcessorService;

public class CarProcessorApplication {
    public static void main(String[] args) {
        CarProcessorRunner runner = new CarProcessorRunner(new CarProcessorService());
        runner.run();
    }
}