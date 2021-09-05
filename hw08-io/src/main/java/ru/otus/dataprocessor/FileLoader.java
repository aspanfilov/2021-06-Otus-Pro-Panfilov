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
        List<Measurement> measurements = readJsonStructure(jsonStructure);

        return measurements;
    }

    private JsonStructure getJsonStructure() {

        JsonStructure jsonStructure;

        try(var jsonReader = Json.createReader(FileLoader.class.getClassLoader().getResourceAsStream(this.fileName))) {
            jsonStructure = jsonReader.read();
        }

        return  jsonStructure;
    }

    private List<Measurement> readJsonStructure(JsonStructure jsonStructure) {
        List<Measurement> measurements = new ArrayList<>();

        for (JsonValue jsonValue : (JsonArray) jsonStructure) {
            measurements.add(readMeasurementFromJsonObject((JsonObject) jsonValue));
        }

        return  measurements;
    }

    private Measurement readMeasurementFromJsonObject(JsonObject jsonObject) {
        JsonString jsName = null;
        JsonNumber jsValue = null;

        for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
            switch (entry.getKey()) {
                case "name":
                    jsName = (JsonString) entry.getValue();
                    break;
                case "value":
                    jsValue = (JsonNumber) entry.getValue();
                    break;
            }
        }

        return new Measurement(jsName.toString(), jsValue.doubleValue());
    }
}
