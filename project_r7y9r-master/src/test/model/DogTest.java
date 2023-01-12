package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DogTest {

    private Dog testDog;

    @BeforeEach
    void setUp() {
        testDog = new Dog("Shadow", "Black", "Terrier", 5, "data/blackterrier.jpg");
    }

    @Test
    void testConstructor() {
        assertEquals("Shadow", testDog.getName());
        assertEquals("Black", testDog.getColor());
        assertEquals("Terrier", testDog.getBreed());
        assertEquals(5, testDog.getAge());
        assertEquals("Up for adoption", testDog.getStatus());
        assertEquals("data/blackterrier.jpg", testDog.getImage());
    }

    @Test
    void testUpdateStatus() {
        testDog.updateStatus();
        assertEquals("Unavailable", testDog.getStatus());

        testDog.updateStatus();
        assertEquals("Up for adoption", testDog.getStatus());
    }


}