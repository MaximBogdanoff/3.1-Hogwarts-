package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("calculation")


public class CalculationController {
    private final Logger LOGGER = LoggerFactory.getLogger(CalculationController.class);
    @GetMapping

    public void calculate() {
        long startTime = System.currentTimeMillis();
        int sum = Stream
                .iterate(1, a -> a + 1).limit(1_000_000)
                .reduce(0, Integer::sum);
        long finishTime = System.currentTimeMillis();
        LOGGER.info("Алгоритм из задания занял " + (finishTime - startTime) + " мс.");

        startTime = System.currentTimeMillis();
        sum = Stream
                .iterate(1, a -> a + 1).limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        finishTime = System.currentTimeMillis();
        LOGGER.info("Алгоритм c parallel stream " + (finishTime - startTime) + " мс.");
    }
}
