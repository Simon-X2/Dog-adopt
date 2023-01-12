package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfDogTest {
    private ListOfDog testDogList;


    @BeforeEach
    void setup(){
        testDogList = new ListOfDog();



    }

    @Test
    void testConstructor(){
        assertTrue(testDogList.isEmpty());
    }

    @Test
    void testAddDog(){

        Dog dog1 = new Dog("Shadow", "Black", "Terrier", 5, "data/blackterrier.jpg");
        Dog dog2 = new Dog("Teddy", "White", "Terrier", 6, "data/blackterrier.jpg");
        Dog dog3 = new Dog("Brun", "Golden", "Golden Retriever", 6, "data/blackterrier.jpg");

        testDogList.addDog(dog1);
        assertEquals(1, testDogList.getSize());

        testDogList.addDog(dog2);
        assertEquals(2, testDogList.getSize());

        testDogList.addDog(dog3);
        assertEquals(3, testDogList.getSize());
    }

    @Test
    void testFindDog(){

        Dog dog1 = new Dog("Shadow", "Black", "Terrier", 5, "data/blackterrier.jpg");
        Dog dog2 = new Dog("Teddy", "White", "Terrier", 6, "data/blackterrier.jpg");
        Dog dog3 = new Dog("Brun", "Golden", "Golden Retriever", 6, "data/blackterrier.jpg");

        testDogList.addDog(dog1);
        assertEquals(dog1, testDogList.findDog("Shadow", testDogList.getListOfDog()));

        testDogList.addDog(dog2);
        testDogList.addDog(dog3);
        assertEquals(dog3, testDogList.findDog("Brun", testDogList.getListOfDog()));

    }

    @Test
    void testFindDogNotThere(){

        Dog dog1 = new Dog("Shadow", "Black", "Terrier", 5, "data/blackterrier.jpg");
        Dog dog2 = new Dog("Teddy", "White", "Terrier", 6, "data/blackterrier.jpg");
        Dog dog3 = new Dog("Brun", "Golden", "Golden Retriever", 6, "data/blackterrier.jpg");;

        testDogList.addDog(dog1);
        testDogList.addDog(dog2);
        testDogList.addDog(dog3);

        assertNull(testDogList.findDog("Bruh", testDogList.getListOfDog()));

    }
}
