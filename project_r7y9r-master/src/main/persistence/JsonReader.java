package persistence;

import model.Dog;
import model.Event;
import model.EventLog;
import model.ListOfDog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//credit: Many codes are gotten from sample code
//Reads the Json file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfDog from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfDog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded your favourite list"));
        return parseListOfDog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses listOfDog from JSON object and returns it
    private ListOfDog parseListOfDog(JSONObject jsonObject) {
        ListOfDog ld = new ListOfDog();
        addDogs(ld, jsonObject);
        return ld;
    }

    // MODIFIES: wr
    // EFFECTS: parses dogs from JSON object and adds them to listOfDog
    private void addDogs(ListOfDog ld, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("dogs");
        for (Object json : jsonArray) {
            JSONObject nextDog = (JSONObject) json;
            addDog(ld, nextDog);
        }
    }

    // MODIFIES: LOD
    // EFFECTS: parses dog from JSON object and adds it to listOfDog
    private void addDog(ListOfDog ld, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String colour = jsonObject.getString("colour");
        String breed = jsonObject.getString("breed");
        String imagePath = jsonObject.getString("breed");
        int age = jsonObject.getInt("age");
        Dog dog = new Dog(name, colour, breed, age, imagePath);
        ld.addDog(dog);
    }
}

