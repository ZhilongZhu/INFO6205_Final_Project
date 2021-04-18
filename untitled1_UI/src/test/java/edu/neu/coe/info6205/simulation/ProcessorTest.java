package edu.neu.coe.info6205.simulation;

import edu.neu.coe.info6205.util.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorTest {


    @Test
    void countCurrentStates() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);


        Processor p = new Processor(n,i,5,0,0,0,5,1,1,0.5,0,0,100);

        p.countCurrentStates();
        assertTrue(p.getCountCurrentUninfected() == 0);
        assertTrue(p.getCountCurrentAllInfected() == 0);

        People people1 = new People(c);
        people1.becomeInfected();
        n.addToNonIso(people1);

        p.countCurrentStates();

        assertTrue(p.getCountCurrentUninfected() == 0);
        assertTrue(p.getCountCurrentAllInfected() == 1);



    }

    @Test
    void moveAll() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        Processor p = new Processor(n,i,5,0,0,0,5,1,1,0.5,0,0,100);
        People people1 = new People(c);
        n.addToNonIso(people1);

        p.moveAll();


        assertTrue((people1.getCoordinates().getX()<=5 && people1.getCoordinates().getX()>=0) && (people1.getCoordinates().getY()<=5 && people1.getCoordinates().getY()>=0));

        for(int count=0;count<10;count++){
            p.moveAll();
        }
        assertTrue((people1.getCoordinates().getX()<=100 && people1.getCoordinates().getX()>=0) && (people1.getCoordinates().getY()<=100 && people1.getCoordinates().getY()>=0));

    }

    @Test
    void vaccinate() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        Processor p = new Processor(n,i,5,0,0,0,5,1,1,0.5,0,0,100);
        People people1 = new People(c);
        n.addToNonIso(people1);

        p.vaccinate();

        assertTrue(!people1.isVACCINATED());



    }

    @Test
    void masking() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        Processor p = new Processor(n,i,5,0,0,1,5,1,1,0.5,0,0,100);
        People people1 = new People(c);
        n.addToNonIso(people1);

        p.masking();

        assertTrue(people1.isMASK());
    }

    @Test
    void virusSpread() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        Processor p = new Processor(n,i,5,0,0,0,15,1,1,0.5,0,0,10);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people4.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        p.virusSpread();

        p.countCurrentStates();
        assertTrue(p.getCountCurrentAllInfected() == 4);


    }

    @Test
    void testing() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people4.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        p.virusSpread();
        p.testing();

        p.countCurrentStates();
        assertTrue(p.getIsolation().getPeopleIsolatedHash().size() == 0);
        assertTrue(p.getCountCurrentAllInfected() == 0);
    }


    @Test
    void dayAdd() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        i.addToIsolation(people1);

        for(int i1 = 0;i1<14;i1++){
            p.dayAdd();
        }

        p.countCurrentStates();

        assertTrue(p.getNonIsolation().getPeopleNonIsoHash().size() == 1);
    }




    @Test
    void getCountTotalInfected() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();
        people2.becomeInfected();
        people3.becomeInfected();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        assertTrue(p.getCountTotalInfected() == 2);

    }

    @Test
    void getCountTotalSuper() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();
        people2.becomeInfected();
        people3.becomeInfected();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        assertTrue(p.getCountTotalSuper() == 1);
    }

    @Test
    void getCountCurrentUninfected() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();
        people2.becomeInfected();
        people3.becomeInfected();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        assertTrue(p.getCountCurrentUninfected() == 1);
    }

    @Test
    void getCountCurrentAllInfected() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();
        people2.becomeInfected();
        people3.becomeInfected();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        assertTrue(p.getCountCurrentAllInfected() == 3);
    }

    @Test
    void getCountTodayContacted() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        p.virusSpread();

        assertTrue(p.getCountTodayContacted() == 3);
    }

    @Test
    void getCountTodayNewPatients() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        p.virusSpread();

        assertTrue(p.getCountTodayNewPatients() == 3);
    }

    @Test
    void getCountIsolation() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        p.virusSpread();

        people4.getStates().clear();
        people4.becomeNormal();

        p.testing();
        p.dayAdd();

        assertTrue(p.getCountIsolation() == 1);
    }

    @Test
    void getCountTodayTest() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        p.testing();

        assertTrue(p.getCountTodayTest() == 4);
    }

    @Test
    void getCountTodayTestPatient() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,0,1,0,15,1,1,0.5,0,0,10);

        p.virusSpread();
        p.testing();

        assertTrue(p.getCountTodayTestPatient() == 4);
    }

    @Test
    void getCountTotalVaccinated() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,1,1,0,15,1,1,0.5,0,0,10);

        p.vaccinate();

        assertTrue(p.getCountTotalVaccinated() == 4);
    }

    @Test
    void getCountTodayVaccinated() {
        NonIsolation n = new NonIsolation();
        Isolation i = new Isolation();
        Coordinates c = new Coordinates(0,0);
        People people1 = new People(c);
        People people2 = new People(c);
        People people3 = new People(c);
        People people4 = new People(c);

        people1.becomeSuper();

        n.addToNonIso(people1);
        n.addToNonIso(people2);
        n.addToNonIso(people3);
        n.addToNonIso(people4);

        Processor p = new Processor(n,i,5,1,1,0,15,1,1,0.5,0,0,10);

        p.vaccinate();

        assertTrue(p.getCountTotalVaccinated() == 4);
    }
}