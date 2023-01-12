package persistence;

import model.Dog;
import model.ListOfDog;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfDog wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptyListOfDog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfDog.json");
        try {
            ListOfDog ld = reader.read();
            assertEquals(0, ld.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderListOfDog() {
        JsonReader reader = new JsonReader("./data/testReaderListOfDog.json");
        try {
            ListOfDog ld = reader.read();
            List<Dog> dogs = ld.getListOfDog();
            assertEquals(2, dogs.size());
            checkDog("Shadow", "Black", "Terrier", 5, dogs.get(0));
            checkDog("Blue", "Blue", "Terrier", 5, dogs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}