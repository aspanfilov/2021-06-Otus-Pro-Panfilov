import com.google.gson.Gson;

import javax.json.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws IOException {
        var person1 = new Person("Bob", 33);
        var person2 = new Person("Tom", 52);
        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);

//        writeJsonByGson(persons);

        var json = createJsonByJavax(persons);

        String fileName = "persons.txt";
        writeJsonByJavax(json, fileName);

        JsonStructure jsonStructure = getJsonFromFileByJavax(fileName);
        List<Person> newPersons = readJsonByJavax(jsonStructure);

    }

    public static JsonArray createJsonByJavax(List<Person> persons) {
        var jsonBuilder = Json.createArrayBuilder();
        for (Person person : persons) {
            jsonBuilder.add(Json.createObjectBuilder()
                    .add("name", person.name)
                    .add("age", person.age));
        }
        JsonArray jsonArray = jsonBuilder.build();

        System.out.println("json by javax: " + jsonArray + "\n");

        return jsonArray;
    }

    public static void writeJsonByJavax(JsonArray json, String fileName) throws IOException {

        File myFile = new File(fileName);
        boolean created = myFile.createNewFile();

        try (var bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName))) {
            byte[] buffer = json.toString().getBytes();
            bufferedOutputStream.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static JsonStructure getJsonFromFileByJavax(String fileName) {

        JsonStructure jsonStructure;

        try (var jsonReader = Json.createReader(Demo.class.getClassLoader().getResourceAsStream(fileName))) {
            jsonStructure = jsonReader.read();
        }

        return jsonStructure;
    }

    public static List<Person> readJsonByJavax(JsonStructure jsonStructure) {

        List<Person> persons = new ArrayList<>();

        for (JsonValue jsonValue : (JsonArray) jsonStructure) {
            persons.add(readPersonFromJsonObjectByJavax((JsonObject) jsonValue));
        }

        return persons;
    }

    public static Person readPersonFromJsonObjectByJavax(JsonObject jsonObject) {
        String name = "";
        int age = 0;

        for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
            var a = entry.getKey();
            var b = entry.getValue();
            var c = jsonObject.get(entry.getKey());
//            switch (entry.getKey()) {
//                case "name":
//                    name = entry.getValue();
//            }
        }

        return new Person(name, age);
    }

    public static void writeJsonByGson(List<Person> persons) {
        var gson = new Gson();
        String json = gson.toJson(persons);

        List<Person> newPersons = gson.fromJson(json, persons.getClass());

        System.out.println(persons);
        System.out.println();
        System.out.println(newPersons);

        System.out.println(json);
    }
}
