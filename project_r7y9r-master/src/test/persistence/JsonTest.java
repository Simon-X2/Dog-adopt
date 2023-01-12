package persistence;

import model.ListOfDog;
import model.Dog;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkDog(String name, String colour, String Breed, int Age, Dog dog) {
        assertEquals(name, dog.getName());

    }
}
