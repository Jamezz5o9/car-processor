package com.carprocessor.main;

import com.carprocessor.main.service.CarProcessorService;

public class CarProcessorApplication {
    public static void main(String[] args) {
        CarProcessorRunner runner = new CarProcessorRunner(new CarProcessorService());
        runner.run();
    }
}