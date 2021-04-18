package edu.neu.coe.info6205.simulation;

import edu.neu.coe.info6205.util.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonIsolationTest {

    @Test
    void addToNonIso() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        NonIsolation nonIsolation = new NonIsolation();

        nonIsolation.addToNonIso(p1);

        assertTrue(nonIsolation.getPeopleNonIsoHash().containsKey(p1));
    }
}