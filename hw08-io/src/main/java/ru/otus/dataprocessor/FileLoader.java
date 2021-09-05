package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileLoader implements Loader {
    String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат

        JsonStructure jsonStructure = getJsonStructure();
        return readJsonStructure(jsonStructure);
    }

    private JsonStructure getJsonStructure() {

        InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(this.fileName);
        if (inputStream == null) {
            throw new FileProcessException("Input file not found");
        }

        JsonStructure jsonStructure;

        try (var jsonReader = Json.createReader(inputStream)) {
            jsonStructure = jsonReader.read();
        }

        return jsonStructure;
    }

    private List<Measurement> readJsonStructure(JsonStructure jsonStructure) {
        List<Measurement> measurements = new ArrayList<>();

        for (JsonValue jsonValue : (JsonArray) jsonStructure) {
            measurements.add(readMeasurementFromJsonObject((JsonObject) jsonValue));
        }

        return measurements;
    }

    private Measurement readMeasurementFromJsonObject(JsonObject jsonObject) {
        JsonString jsName = null;
        JsonNumber jsValue = null;

        for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
            switch (entry.getKey()) {
                case "name" -> jsName = (JsonString) entry.getValue();
                case "value" -> jsValue = (JsonNumber) entry.getValue();
            }
        }

        if (jsName == null || jsValue == null) {
            throw new FileProcessException("Incorrect data from input file");
        }
        return new Measurement(jsName.toString(), jsValue.doubleValue());
    }
}
