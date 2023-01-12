package model;

import org.json.JSONObject;
import persistence.Writeable;

import java.awt.*;

//Represents a dog of having an id name, age, and breed and adoption status
public class Dog implements Writeable {
    private int age;
    private String name;
    private String color;
    private String breed;
    private String adoptionStatus;
    private String imagePath;


    //REQUIRES: AGE <= 15
    //EFFECTS: creates instance of dog with given name
    //         creates instance of dog with given age <= 15
    //         creates instance of dog with adoption status of "up for adoption"
    public Dog(String name, String color, String breed, int age, String imagePath) {
        this.name = name;
        this.color = color;
        this.breed = breed;
        this.age = age;
        this.adoptionStatus = "Up for adoption";
        this.imagePath = imagePath;
    }

    //MODIFIES: this
    //EFFECTS: changes the adoption status from "Up for adoption to unavailable and vice versa
    public void updateStatus() {
        if (this.adoptionStatus.equals("Up for adoption")) {
            this.adoptionStatus = "Unavailable";
        } else {
            this.adoptionStatus = "Up for adoption";
        }
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;

    }

    public String getBreed() {
        return this.breed;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return adoptionStatus;
    }

    public String getImage() {
        return imagePath;
    }

    @Override
    // *Credit: From sample code
    // EFFECT: changes this object into a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("colour", color);
        json.put("breed", breed);
        json.put("age", age);
        json.put("image", imagePath);
        return json;
    }


}
