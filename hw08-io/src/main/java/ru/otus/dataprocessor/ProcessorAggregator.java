package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value

        Map<String, Double> groupedMeasurements = new HashMap<>();
        for (Measurement measurement : data) {
            var value = groupedMeasurements.get(measurement.getName());
            value = value == null ? 0 : value;
            groupedMeasurements.put(measurement.getName(), measurement.getValue() + value);
        }

        return groupedMeasurements;

    }
}
