package ru.otus.dataprocessor;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.PrimitiveIterator;

public class FileSerializer implements Serializer {
    private String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        //формирует результирующий json и сохраняет его в файл

        var json = createJson(data);
        writeJson(json);

    }

    private JsonObject createJson(Map<String, Double> data) {

        var jsonBuilder = Json.createObjectBuilder();

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            jsonBuilder.add(entry.getKey().replace("\"", ""), entry.getValue());
        }

        JsonObject jsonObject = jsonBuilder.build();

        return jsonObject;
    }

    private void writeJson(JsonObject json) throws IOException {

        File newFile = new File(this.fileName);
        boolean created = newFile.createNewFile();

        try (var bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName))) {
            byte[] buffer = json.toString().getBytes();
            bufferedOutputStream.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
