package persistence;


import model.Dog;
import model.ListOfDog;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfDog ld = new ListOfDog();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyListOfDog() {
        try {
            ListOfDog ld = new ListOfDog();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfDogs.json");
            writer.open();
            writer.write(ld);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfDogs.json");
            ld = reader.read();
            assertEquals(0, ld.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterListOfDog() {
        try {
            ListOfDog ld = new ListOfDog();
            ld.addDog(new Dog("Shadow", "Black", "Terrier", 5, "data/blackterrier.jpg"));
            ld.addDog(new Dog("Blue", "Blue", "Terrier", 5, "data/blackterrier.jpg"));
            JsonWriter writer = new JsonWriter("./data/testWriterListofDogs.json");
            writer.open();
            writer.write(ld);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterListofDogs.json");
            ld = reader.read();
            List<Dog> dogs = ld.getListOfDog();
            assertEquals(2, dogs.size());
            checkDog("Shadow", "Black", "Terrier", 5, dogs.get(0));
            checkDog("Blue", "Blue", "Terrier", 5, dogs.get(1));

        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
