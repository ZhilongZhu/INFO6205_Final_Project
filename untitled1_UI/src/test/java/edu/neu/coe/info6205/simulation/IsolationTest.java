package edu.neu.coe.info6205.simulation;

import edu.neu.coe.info6205.util.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsolationTest {

    @Test
    void addToIsolation() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        Isolation isolation = new Isolation();

        isolation.addToIsolation(p1);
        assertTrue(isolation.getPeopleIsolatedHash().containsKey(p1));
    }

    @Test
    void updateIsolationDay() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        Isolation isolation = new Isolation();

        isolation.addToIsolation(p1);
        assertTrue(isolation.getPeopleIsolatedHash().get(p1) == 0);
        isolation.updateIsolationDay();
        assertTrue(isolation.getPeopleIsolatedHash().get(p1) == 1);

    }

    @Test
    void getPeopleOut() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        Isolation isolation = new Isolation();

        isolation.addToIsolation(p1);

        for(int i=0;i<14;i++){
            isolation.updateIsolationDay();
        }

        assertTrue(isolation.getPeopleOut().contains(p1));
    }
}