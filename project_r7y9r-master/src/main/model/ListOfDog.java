package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

//Represents a list of dogs
public class ListOfDog implements Writeable {

    private List<Dog> listOfDog;


    //Effects: constructs an empty list of dogs
    public ListOfDog() {
        this.listOfDog = new ArrayList<>();

    }

    //REQUIRES: Dog cannot be duplicate
    //MODIFIES: this
    //EFFECT: adds a dog to the favourite list
    public void addDog(Dog dog) {
        listOfDog.add(dog);
        EventLog.getInstance().logEvent(new Event("Added " + dog.getName() + " to your favourite list."));
    }

    //REQUIRES: Dog cannot be duplicate
    //MODIFIES: this
    //EFFECT: adds a dog to the favourite list
    public void add(Dog dog) {
        listOfDog.add(dog);
    }

    //EFFECT: finds the dog with the given name in a list of dogs and returns it, does nothing otherwise
    public Dog findDog(String dog, List<Dog> listOfDog) {
        for (Dog d : listOfDog) {
            if (dog.equals(d.getName())) {
                return d;
            }
        }
        System.out.println("SELECTION NOT VALID");
        return null;

    }

    //EFFECT: Returns a list of dog
    public List<Dog> getListOfDog() {
        return listOfDog;

    }

    //EFFECT: Returns True if list is empty
    public boolean isEmpty() {
        return listOfDog.isEmpty();

    }

    //EFFECT: Returns the size of the list
    public int getSize() {
        return listOfDog.size();

    }

    // *Credit: From sample code
    // EFFECTS: Changes this object into JSON code
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("dogs", dogsToJson());

        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    // *Credit: From sample code
    private JSONArray dogsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Dog d : listOfDog) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
