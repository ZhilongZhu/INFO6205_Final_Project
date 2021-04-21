package edu.neu.coe.info6205.simulation;

import edu.neu.coe.info6205.util.Coordinates;
import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

class PeopleTest {

    @org.junit.jupiter.api.Test
    void becomeNormal() {
        Coordinates c = new Coordinates(0,0);
        People p = new People(c);
        p.becomeNormal();
        assertEquals(true,p.getStates().contains(StateEnum.NORMAL));

    }

    @org.junit.jupiter.api.Test
    void becomeSuper() {
        Coordinates c = new Coordinates(0,0);
        People p = new People(c);
        p.becomeSuper();
        assertEquals(true,p.getStates().contains(StateEnum.SUPER));
    }

    @org.junit.jupiter.api.Test
    void becomeInfected() {
        Coordinates c = new Coordinates(0,0);
        People p = new People(c);
        p.becomeInfected();
        assertEquals(true,p.getStates().contains(StateEnum.INFECTED));
    }

    @org.junit.jupiter.api.Test
    void removeAllState() {
        Coordinates c = new Coordinates(0,0);
        People p = new People(c);
        p.removeAllState();
        assertEquals(0,p.getStates().size());
    }

    @org.junit.jupiter.api.Test
    void addContacts() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);
        People p2 = new People(c);

        p1.addContacts(p2);
        assertEquals(true,p1.getContacted().contains(p2));
    }

    @org.junit.jupiter.api.Test
    void addState() {
        Coordinates c = new Coordinates(0,0);
        People p = new People(c);
        p.addState(StateEnum.NORMAL);
        assertEquals(true,p.getStates().contains(StateEnum.NORMAL));

    }

    @org.junit.jupiter.api.Test
     void stateCheck() {
        Coordinates c = new Coordinates(0,0);
        People p = new People(c);

        try{
            p.getStates().add(StateEnum.NORMAL);
            p.getStates().add(StateEnum.SUPER);
            p.stateCheck();
            fail("No exception thrown.");
        } catch (Exception e){
           assertTrue(e.getMessage().contains("PersonStatesError"));
        }
    }

    @org.junit.jupiter.api.Test
    void singleMove() {
        Coordinates c = new Coordinates(0,0);
        People p = new People(c);

        p.singleMove(5,100);
        assertTrue((p.getCoordinates().getX()<=5 && p.getCoordinates().getX()>=0) && (p.getCoordinates().getY()<=5 && p.getCoordinates().getY()>=0));

        for(int i=0;i<10;i++){
            p.singleMove(5,100);
        }
        assertTrue((p.getCoordinates().getX()<=100 && p.getCoordinates().getX()>=0) && (p.getCoordinates().getY()<=100 && p.getCoordinates().getY()>=0));


    }

    @org.junit.jupiter.api.Test
    void calcDistance() {
        Coordinates c = new Coordinates(0,0);
        Coordinates c2 = new Coordinates(0,1);
        Coordinates c3 = new Coordinates(1,0);
        Coordinates c4 = new Coordinates(1,1);


        People p1 = new People(c);
        People p2 = new People(c);


        assertTrue(p1.calcDistance(p2) == 0);

        p2.setCoordinates(c2);


        assertTrue(p1.calcDistance(p2) == 1);

        p2.setCoordinates(c4);

        assertTrue(p1.calcDistance(p2)==Math.sqrt(2.0d));



    }

    @org.junit.jupiter.api.Test
    void isWithinDistance() {
        Coordinates c = new Coordinates(0,0);
        Coordinates c2 = new Coordinates(0,1);
        Coordinates c3 = new Coordinates(1,0);
        Coordinates c4 = new Coordinates(1,1);


        People p1 = new People(c);
        People p2 = new People(c);

        assertTrue(p1.isWithinDistance(p2,5));
    }

    @org.junit.jupiter.api.Test
    void isNORMAL() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        p1.addState(StateEnum.NORMAL);

        assertTrue(p1.isNORMAL());

    }



    @org.junit.jupiter.api.Test
    void isSUPER() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        p1.addState(StateEnum.SUPER);

        assertTrue(p1.isNORMAL());
    }



    @org.junit.jupiter.api.Test
    void isVACCINATED() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        p1.addState(StateEnum.VACCINATED);

        assertTrue(p1.isNORMAL());
    }

    @org.junit.jupiter.api.Test
    void isMASK() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        p1.addState(StateEnum.MASK);

        assertTrue(p1.isNORMAL());
    }

    @org.junit.jupiter.api.Test
    void isINFECTED() {
        Coordinates c = new Coordinates(0,0);
        People p1 = new People(c);

        p1.addState(StateEnum.INFECTED);

        assertTrue(p1.isNORMAL());
    }
}