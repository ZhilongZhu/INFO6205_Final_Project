package edu.neu.coe.info6205.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * a class that deal with NonIsolation and Isolation
 */
public class Processor {
    private Random random;

    private NonIsolation nonIsolation;
    private Isolation isolation;

    private List<People> tempRemove; //used to collect contacts for remove from NonIsolation
    private int coordinateRange;

    private List<People> normals;
    private List<People> infects;
    private List<People> supers;
    private List<People> todayContacted;

    private int countCurrentUninfected;
    private int countCurrentAllInfected;

    private int countTotalInfected;
    private int countTotalSuper;

    private int countTodayContacted;
    private int countTodayNewPatients;

    private int countIsolation;

    private int countTodayTest;
    private int countTodayTestPatient;

    private int countTotalVaccinated;
    private int countTodayVaccinated;


    /**
     * build list of people based on its state,
     * before build, all the previous records will be removed
     * update countUninfected and countInfected
     */
    public void countCurrentStates() {
        this.normals.clear();
        this.infects.clear();
        this.supers.clear();
        for (People p : this.nonIsolation.getPeopleNonIsoHash().keySet()) {
            if (p.isNORMAL()) {
                this.normals.add(p);
            }
            if (p.isINFECTED()) {
                this.infects.add(p);
            }
            if (p.isSUPER()) {
                this.supers.add(p);
            }

        }

        this.countCurrentUninfected = this.normals.size();
        this.countCurrentAllInfected = this.infects.size() + this.supers.size();
    }


    //ratio should be a decimal between 0 and 1, i.e. 0.1
    private int peopleSingleMoveRange;  // control the range of a person move once
    private double vaccinateRatio;      // = People(to be Vaccinated one day) / People(in NonIsolation)
    private double testRatio;           // = People(to be Tested one day) / People(in NonIsolation)
    private double maskRatio;           // = People(to get masked one day) / People(in NonIsolation)
    private double safeDistance;        // with this distance, a person may be infected
    private double sSuper;              // S value of super spreader
    private double sInfected;           // S value of non-super infected
    private double superRatio;          // ratio determining the probability of one turning into super when being infected
    private double maskEffect;          // determining how effective of a mask
    private double vaccineEffect;       // determining how effective of a vaccine

    public Processor(NonIsolation n, Isolation i, String peopleSingleMoveRangeS, String vaccinateRatioS, String testRatioS, String maskRatioS, String safeDistanceS, String sSuperS, String sInfectedS, String superRatioS, String maskEffectS, String vaccineEffectS, String coordinateRangeS) {
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

        this.nonIsolation = nonIsolation;
        this.isolation = isolation;
        this.peopleSingleMoveRange = peopleSingleMoveRange;
        this.vaccinateRatio = vaccinateRatio;
        this.testRatio = testRatio;
        this.maskRatio = maskRatio;
        this.safeDistance = safeDistance;
        this.sSuper = sSuper;
        this.sInfected = sInfected;
        this.superRatio = superRatio;
        this.maskEffect = maskEffect;
        this.vaccineEffect = vaccineEffect;

        this.random = new Random();

        this.tempRemove = new ArrayList<>();
        this.coordinateRange = coordinateRange;

        this.normals = new ArrayList<>();
        this.infects = new ArrayList<>();
        this.supers = new ArrayList<>();
        this.todayContacted = new ArrayList<>();

        this.countCurrentUninfected = 0;
        this.countCurrentAllInfected = 0;

        this.countTodayContacted = 0;
        this.countTodayNewPatients = 0;

        this.countIsolation = 0;

        this.countTodayTest = 0;
        this.countTodayTestPatient = 0;

        this.countTotalVaccinated = 0;
        this.countTodayVaccinated = 0;

        countCurrentStates();

        this.countTotalInfected += this.infects.size();
        this.countTotalSuper += this.supers.size();

    }

    public Processor(NonIsolation nonIsolation, Isolation isolation, int peopleSingleMoveRange, double vaccinateRatio, double testRatio, double maskRatio, double safeDistance, double sSuper, double sInfected, double superRatio, double maskEffect, double vaccineEffect, int coordinateRange) {
        this.nonIsolation = nonIsolation;
        this.isolation = isolation;
        this.peopleSingleMoveRange = peopleSingleMoveRange;
        this.vaccinateRatio = vaccinateRatio;
        this.testRatio = testRatio;
        this.maskRatio = maskRatio;
        this.safeDistance = safeDistance;
        this.sSuper = sSuper;
        this.sInfected = sInfected;
        this.superRatio = superRatio;
        this.maskEffect = maskEffect;
        this.vaccineEffect = vaccineEffect;

        this.random = new Random();
        this.tempRemove = new ArrayList<>();
        this.coordinateRange = coordinateRange;

        this.normals = new ArrayList<>();
        this.infects = new ArrayList<>();
        this.supers = new ArrayList<>();
        this.todayContacted = new ArrayList<>();

        this.countCurrentUninfected = 0;
        this.countCurrentAllInfected = 0;

        this.countTodayContacted = 0;
        this.countTodayNewPatients = 0;

        this.countIsolation = 0;

        this.countTodayTest = 0;
        this.countTodayTestPatient = 0;

        this.countTotalVaccinated = 0;
        this.countTodayVaccinated = 0;


        countCurrentStates();

        this.countTotalInfected += this.infects.size();
        this.countTotalSuper += this.supers.size();

    }


    /**
     * set all people nonIsolation move once
     */
    public void moveAll() {
        for (People p : this.nonIsolation.getPeopleNonIsoHash().keySet()) {
            p.singleMove(this.peopleSingleMoveRange, this.coordinateRange);
        }
    }

    /**
     * randomly choose people in nonIsolation to be vaccinated
     */
    public void vaccinate() {
        this.countTodayVaccinated = 0;
        for (People p : this.nonIsolation.getPeopleNonIsoHash().keySet()) {
            double r = this.random.nextDouble();
            if (r <= this.vaccinateRatio) {
                if(!p.isVACCINATED()){
                    p.addState(StateEnum.VACCINATED);
                    this.countTodayVaccinated += 1;

                }
            }

        }
        this.countTotalVaccinated += this.countTodayVaccinated;
    }

    /**
     * randomly choose people in nonIsolation to be masked
     */
    public void masking() {
        for (People p : this.nonIsolation.getPeopleNonIsoHash().keySet()) {
            double r = this.random.nextDouble();
            if (r <= this.maskRatio) {
                p.addState(StateEnum.MASK);
            }

        }
    }

    /**
     * simulate the virus spreading in NonIsolation
     * before count infected, set countTodayContacted = 0
     * before count infected, set countTodayNewPatients = 0
     */
    public void virusSpread() {
        this.todayContacted.clear();
        this.countTodayContacted = 0;
        this.countTodayNewPatients = 0;

        //todo 新建templist 装所有病人， check
        List<People> templist = new ArrayList<>();
        for (Iterator<People> it = this.nonIsolation.getPeopleNonIsoHash().keySet().iterator(); it.hasNext(); ) {
            People people = it.next();
            if (people.isINFECTED() || people.isSUPER()) {
                templist.add(people);
            }
        }
        for (People p : templist) { //从templist搞
            if (p.isINFECTED()) {

                p.getContacted().clear();//保证contacts 是当天的
                infectedSpread(p);
            } else if (p.isSUPER()) {

                p.getContacted().clear();//保证contacts 是当天的
                superSpread(p);
            }
        }
        this.countTodayContacted = this.todayContacted.size();


    }

    /**
     * simulate the process that one person get contacted with infected/super and get infected
     * update countTodayNewPatients
     *
     * @param targetP the person who is going to infected
     * @param sValue
     * @return
     */
    private boolean infect(People targetP, double sValue) {


        double maskE = 1.0d;
        if (targetP.isMASK()) maskE = this.maskEffect;

        double vaccineE = 1.0d;
        if (targetP.isVACCINATED()) vaccineE = this.vaccineEffect;

        double rInfectOrNormal = this.random.nextDouble();
        double rSuperOrNot = this.random.nextDouble();

        if (rInfectOrNormal < sValue * maskE * vaccineE) {
            this.countTodayNewPatients += 1;

            if (rSuperOrNot < this.superRatio) {
                targetP.becomeSuper();
                this.countTotalSuper += 1;
            } else {
                targetP.becomeInfected();
                this.countTotalInfected += 1;
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * basically this func does the following things:
     * 1 searching for contacts
     * 2 infecting contacts
     * 3 adding to contact list
     * 4 update countTodayContacted
     *
     * @param p
     */
    private void superSpread(People p) {

        for (People targetP : this.nonIsolation.getPeopleNonIsoHash().keySet()) {
            if (p.equals(targetP)) {

            } else {
                double distance = p.calcDistance(targetP);
                if (distance < this.safeDistance) {
                    p.addContacts(targetP);
                    if (!this.todayContacted.contains(targetP)) {
                        this.todayContacted.add(targetP);
                    }
//                this.countTodayContacted += 1;

                    if (targetP.isNORMAL()) {
                        this.infect(targetP, this.sSuper);
                    }


                }
            }
        }
    }

    private void infectedSpread(People p) {
        for (People targetP : this.nonIsolation.getPeopleNonIsoHash().keySet()) {
            if (p.equals(targetP)) {

            } else {
                double distance = p.calcDistance(targetP);
                if (distance < this.safeDistance) {
                    p.addContacts(targetP);
                    if (!this.todayContacted.contains(targetP)) {
                        this.todayContacted.add(targetP);
                    }
//                this.countTodayContacted += 1;

                    if (targetP.isNORMAL()) {
                        this.infect(targetP, this.sInfected);
                    }


                }
            }
        }
    }


    /**
     * simulate the testing
     * random choose people in NoIsolation to do test
     * update countTodayTest
     * update countTodayTestPatient
     */
    public void testing() {
        this.tempRemove.clear();
        this.countTodayTest = 0;
        this.countTodayTestPatient = 0;

        for (Iterator<People> it = this.nonIsolation.getPeopleNonIsoHash().keySet().iterator(); it.hasNext(); ) {
            double r = this.random.nextDouble();
            People p = it.next();
            if (r <= this.testRatio) {
                this.countTodayTest += 1;

                if (doTest(p)) {
                    it.remove();
                    this.countTodayTestPatient += 1;
                }
            }
        }

        for (People p : this.tempRemove) {
            this.nonIsolation.getPeopleNonIsoHash().remove(p);
        }
//        for (People p : this.nonIsolation.getPeopleNonIsoHash().keySet()) {
//            double r = this.random.nextDouble();
//            if (r <= this.testRatio) {
//                doTest(p);
//            }
//        }
    }

    /**
     * simulate the test procedures, in general, a test would do:
     * NORMAL => nothing
     * SUPER => remove SUPER from simulation, put contacted people in Isolation
     * INFECTED => remove INFECTED from simulation, put contacted people in Isolation
     *
     * @param p
     */
    private boolean doTest(People p) {
//        this.nonIsolation.getPeopleNonIsoHash().remove(p);
        if (p.isINFECTED() || p.isSUPER()) {
            for (Iterator<People> it = p.getContacted().listIterator(); it.hasNext(); ) {
                People pC = it.next();
                putPeopleInIso(pC); //身边人都要隔离
                //todo 从NonIsolation消失 check
                if (!this.tempRemove.contains(pC)) {
                    this.tempRemove.add(pC);
                }
            }
            return true;
//            for (People pContacted : p.getContacted()) {
//                putPeopleInIso(pContacted);
//            }
        } else {
            // do nothing
            return false;
        }

    }

    /**
     * simulate the procedure of putting people into Isolation(QUARANTINED)
     * before actually put p into Isolation, kick out those who have already been infected from simulation
     *
     * @param p
     */
    private void putPeopleInIso(People p) {
        //this.nonIsolation.getPeopleNonIsoHash().remove(p);
        if (p.isINFECTED() || p.isSUPER()) {
            // kicked out the simulation without putting in Isolation

        } else if (p.isNORMAL()) {
            this.isolation.addToIsolation(p);
        }
    }

    /**
     * get People back from Isolation to NonIsolation
     */
    public void putPeopleBackInNonIso() {
        List<People> tempP = this.isolation.getPeopleOut();
        for (People p : tempP) {
            this.nonIsolation.addToNonIso(p);
        }
    }

    public void dayAdd() {
        this.isolation.updateIsolationDay();
        this.putPeopleBackInNonIso();
        this.countIsolation = this.isolation.getPeopleIsolatedHash().size();

//        day.nextDay();

    }


    public NonIsolation getNonIsolation() {
        return nonIsolation;
    }

    public void setNonIsolation(NonIsolation nonIsolation) {
        this.nonIsolation = nonIsolation;
    }

    public Isolation getIsolation() {
        return isolation;
    }

    public void setIsolation(Isolation isolation) {
        this.isolation = isolation;
    }

    public int getPeopleSingleMoveRange() {
        return peopleSingleMoveRange;
    }

    public void setPeopleSingleMoveRange(int peopleSingleMoveRange) {
        this.peopleSingleMoveRange = peopleSingleMoveRange;
    }

    public double getVaccinateRatio() {
        return vaccinateRatio;
    }

    public void setVaccinateRatio(double vaccinateRatio) {
        this.vaccinateRatio = vaccinateRatio;
    }

    public double getTestRatio() {
        return testRatio;
    }

    public void setTestRatio(double testRatio) {
        this.testRatio = testRatio;
    }

    public double getMaskRatio() {
        return maskRatio;
    }

    public void setMaskRatio(double maskRatio) {
        this.maskRatio = maskRatio;
    }

    public double getSafeDistance() {
        return safeDistance;
    }

    public void setSafeDistance(double safeDistance) {
        this.safeDistance = safeDistance;
    }

    public double getsSuper() {
        return sSuper;
    }

    public void setsSuper(double sSuper) {
        this.sSuper = sSuper;
    }

    public double getsInfected() {
        return sInfected;
    }

    public void setsInfected(double sInfected) {
        this.sInfected = sInfected;
    }

    public double getSuperRatio() {
        return superRatio;
    }

    public void setSuperRatio(double superRatio) {
        this.superRatio = superRatio;
    }

    public double getMaskEffect() {
        return maskEffect;
    }

    public void setMaskEffect(double maskEffect) {
        this.maskEffect = maskEffect;
    }

    public double getVaccineEffect() {
        return vaccineEffect;
    }

    public void setVaccineEffect(double vaccineEffect) {
        this.vaccineEffect = vaccineEffect;
    }

    public List<People> getNormals() {
        return normals;
    }

    public List<People> getInfects() {
        return infects;
    }

    public List<People> getSupers() {
        return supers;
    }

    public int getCountTotalInfected() {
        return countTotalInfected;
    }

    public int getCountTotalSuper() {
        return countTotalSuper;
    }

    public int getCountCurrentUninfected() {
        return countCurrentUninfected;
    }

    public int getCountCurrentAllInfected() {
        return countCurrentAllInfected;
    }

    public int getCountTodayContacted() {
        return countTodayContacted;
    }

    public int getCountTodayNewPatients() {
        return countTodayNewPatients;
    }

    public int getCountIsolation() {
        return countIsolation;
    }

    public int getCountTodayTest() {
        return countTodayTest;
    }

    public int getCountTodayTestPatient() {
        return countTodayTestPatient;
    }

    public int getCountTotalVaccinated() {
        return countTotalVaccinated;
    }

    public int getCountTodayVaccinated() {
        return countTodayVaccinated;
    }
}
