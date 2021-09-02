package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements Loader {
    String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат

//        List<Measurement> result = new ArrayList<>();
        Object result;

        try (var objectInputStream = new ObjectInputStream(new FileInputStream(this.fileName))) {
            result = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
