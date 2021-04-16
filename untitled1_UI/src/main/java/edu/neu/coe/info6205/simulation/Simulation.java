package edu.neu.coe.info6205.simulation;

import edu.neu.coe.info6205.util.CoordinateGenerator;
import edu.neu.coe.info6205.util.Coordinates;
import edu.neu.coe.info6205.util.Day;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Simulation {
    private Processor processor;
    private Day day;

    public void initProcessorFromUser(String peopleSingleMoveRangeS, String vaccinateRatioS, String testRatioS, String maskRatioS, String safeDistanceS, String sSuperS, String sInfectedS, String superRatioS, String maskEffectS, String vaccineEffectS, String coordinateRangeS, String numberOfPeopleInitS, String initRatioOfSuperS, String initRatioOfInfectedS) {

        int peopleSingleMoveRange = Integer.parseInt(peopleSingleMoveRangeS);
        double vaccinateRatio = Double.parseDouble(vaccinateRatioS);
        double testRatio = Double.parseDouble(testRatioS);
        double maskRatio = Double.parseDouble(maskRatioS);
        double safeDistance = Double.parseDouble(safeDistanceS);
        double sSuper = Double.parseDouble(sSuperS);
        double sInfected = Double.parseDouble(sInfectedS);
        double superRatio = Double.parseDouble(superRatioS);
        double maskEffect = Double.parseDouble(maskEffectS);
        double vaccineEffect = Double.parseDouble(vaccineEffectS);

        int coordinateRange = Integer.parseInt(coordinateRangeS);
        int numberOfPeopleInit = Integer.parseInt(numberOfPeopleInitS);

        double initRatioOfSuper = Double.parseDouble(initRatioOfSuperS);
        double initRatioOfInfected = Double.parseDouble(initRatioOfInfectedS);


        Random r = new Random();
        Day d = new Day();
        CoordinateGenerator coordinateGenerator = new CoordinateGenerator();
        NonIsolation nonIsolation = new NonIsolation();
        try {
            for (int i = 0; i < numberOfPeopleInit; i++) {
                Coordinates coordinates = coordinateGenerator.generateRandomCoordinates(coordinateRange);
                double roll = r.nextDouble();
                if (roll > initRatioOfInfected) {
                    People people = new People(coordinates);
                    nonIsolation.addToNonIso(people);
                } else if (roll > initRatioOfSuper) {
                    People people = new People(coordinates, StateEnum.INFECTED);
                    nonIsolation.addToNonIso(people);
                } else {
                    People people = new People(coordinates, StateEnum.SUPER);
                    nonIsolation.addToNonIso(people);
                }
            }
        } catch (Exception e) {
            System.out.println("init fail : NonIsolation Build Error");
        }

        Isolation isolation = new Isolation();

        this.processor = new Processor(nonIsolation, isolation, peopleSingleMoveRange, vaccinateRatio, testRatio, maskRatio, safeDistance, sSuper, sInfected, superRatio, maskEffect, vaccineEffect, coordinateRange);
        this.day = d;
        processor.masking();

    }

    public void initProcessorFromProp() {
        Properties p = new Properties();
        try {
            InputStream in = ClassLoader.getSystemResourceAsStream("test.properties");
            p.load(in);
        } catch (Exception e) {
            System.out.println("no configure file found");
            return;
        }

        int peopleSingleMoveRange = Integer.parseInt(p.getProperty("PeopleSingleMoveRange"));
        double vaccinateRatio = Double.parseDouble(p.getProperty("VaccinateRatio"));
        double testRatio = Double.parseDouble(p.getProperty("TestRatio"));
        double maskRatio = Double.parseDouble(p.getProperty("MaskRatio"));
        double safeDistance = Double.parseDouble(p.getProperty("SafeDistance"));
        double sSuper = Double.parseDouble(p.getProperty("SSuper"));
        double sInfected = Double.parseDouble(p.getProperty("SInfected"));
        double superRatio = Double.parseDouble(p.getProperty("SuperRatio"));
        double maskEffect = Double.parseDouble(p.getProperty("MaksEffect"));
        double vaccineEffect = Double.parseDouble(p.getProperty("VaccineEffect"));

        int coordinateRange = Integer.parseInt(p.getProperty("CoordinateRange"));
        int numberOfPeopleInit = Integer.parseInt(p.getProperty("NumberOfPeopleInit"));

        double initRatioOfSuper = Double.parseDouble(p.getProperty("InitRatioOfSuper"));
        double initRatioOfInfected = Double.parseDouble(p.getProperty("InitRatioOfInfected"));

        Random r = new Random();
        Day d = new Day();
        CoordinateGenerator coordinateGenerator = new CoordinateGenerator();
        NonIsolation nonIsolation = new NonIsolation();
        try {
            for (int i = 0; i < numberOfPeopleInit; i++) {
                Coordinates coordinates = coordinateGenerator.generateRandomCoordinates(coordinateRange);
                double roll = r.nextDouble();
                if (roll > initRatioOfInfected) {
                    People people = new People(coordinates);
                    nonIsolation.addToNonIso(people);
                } else if (roll > initRatioOfSuper) {
                    People people = new People(coordinates, StateEnum.INFECTED);
                    nonIsolation.addToNonIso(people);
                } else {
                    People people = new People(coordinates, StateEnum.SUPER);
                    nonIsolation.addToNonIso(people);
                }
            }
        } catch (Exception e) {
            System.out.println("init fail : NonIsolation Build Error");
        }

        Isolation isolation = new Isolation();

        this.processor = new Processor(nonIsolation, isolation, peopleSingleMoveRange, vaccinateRatio, testRatio, maskRatio, safeDistance, sSuper, sInfected, superRatio, maskEffect, vaccineEffect, coordinateRange);
        this.day = d;

//        processor.moveAll();
//
//        processor.vaccinate();
//
        processor.masking();
//
//        processor.virusSpread();
//
//        processor.testing();
    }

    public void oneRound() {
        //this.processor.putPeopleBackInNonIso();

        this.processor.moveAll();

        this.processor.vaccinate(); //countTotalVaccinated & countTodayVaccinated

        this.processor.virusSpread();//countTodayContacted & countTodayNewPatients

        this.processor.testing();//countTodayTest & countTodayTestPatient

        this.processor.dayAdd();//countIsolation

        this.processor.countCurrentStates();//build lists of Normals, Infected, Super  & countUninfected & countInfected

        this.day.nextDay();


    }

    public List<People> getCurrentNormals() {
        return this.processor.getNormals();
    }

    public List<People> getCurrentInfected() {
        return this.processor.getInfects();
    }

    public List<People> getCurrentSupers() {
        return this.processor.getSupers();
    }

    public int getCountTotalInfected() {
        return this.processor.getCountTotalInfected();
    }

    public int getCountTotalSuper() {
        return this.processor.getCountTotalSuper();
    }

    public int getCountCurrentUninfected() {
        return this.processor.getCountCurrentUninfected();
    }

    public int getCountCurrentAllInfected() {
        return this.processor.getCountCurrentAllInfected();
    }

    public int getCountTodayContacted() {
        return this.processor.getCountTodayContacted();
    }

    public int getCountTodayNewPatients() {
        return this.processor.getCountTodayNewPatients();
    }

    public int getCountIsolation() {
        return this.processor.getCountIsolation();
    }

    public int getCountTodayTest() {
        return this.processor.getCountTodayTest();
    }

    public int getCountTodayTestPatient() {
        return this.processor.getCountTodayTestPatient();
    }

    public int getCountTotalVaccinated() {
        return this.processor.getCountTotalVaccinated();
    }

    public int getCountTodayVaccinated() {
        return this.processor.getCountTodayVaccinated();
    }
}
