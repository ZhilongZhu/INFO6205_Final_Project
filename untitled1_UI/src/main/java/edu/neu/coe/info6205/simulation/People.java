package edu.neu.coe.info6205.simulation;


import edu.neu.coe.info6205.util.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class People {
    private Coordinates coordinates;
    private List<StateEnum> states;
    private List<People> contacted;

    public People(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.states = new ArrayList<>();
        this.contacted = new ArrayList<>();
        this.states.add(StateEnum.NORMAL);//initial state is NORMAL
    }

    public People(Coordinates coordinates, StateEnum initEnum) throws Exception {
        this.coordinates = coordinates;
        this.states = new ArrayList<>();
        this.states.add(initEnum);
        this.stateCheck();
        this.contacted = new ArrayList<>();
    }


    public List<People> getContacted() {
        return contacted;
    }


    public void becomeNormal() {

//        this.removeAllState();
        if (!this.states.contains(StateEnum.NORMAL)) {
            this.states.add(StateEnum.NORMAL);
        }
    }

    public void becomeSuper() {

        if (this.states.contains(StateEnum.NORMAL)) {
            this.states.remove(StateEnum.NORMAL);
        }
        if (this.states.contains(StateEnum.INFECTED)) {
            this.states.remove(StateEnum.INFECTED);
        }
        if (!this.states.contains(StateEnum.SUPER)) {
            this.states.add(StateEnum.SUPER);
        }
    }

    public void becomeInfected() {
        if (this.states.contains(StateEnum.NORMAL)) {
            this.states.remove(StateEnum.NORMAL);
        }
        if (this.states.contains(StateEnum.SUPER)) {
            this.states.remove(StateEnum.SUPER);
        }
        if (!this.states.contains(StateEnum.INFECTED)) {
            this.states.add(StateEnum.INFECTED);
        }
    }

    /**
     * remove all states of one person
     */
    public void removeAllState() {
        for(int i=0;i<this.states.size();i++){
            this.states.remove(i);
        }
    }

    /**
     * add person to the contact list
     *
     * @param p contacted person
     */
    public void addContacts(People p) {
        if (!this.contacted.contains(p)) {
            this.contacted.add(p);
        }
    }

    /**
     * todo clear Contacts
     */

    /**
     * add one new state to a person
     *
     * @param s the new state (especially VACCINATED and MASK)
     */
    public void addState(StateEnum s) {
        this.states.add(s);
        try {
            this.stateCheck();
        } catch (Exception e) {
            this.states.remove(s);
        }
    }

    /**
     * add constraints on state of one person:
     * 1 a person cannot be NORMAL and CONFIRMED at the same time (alterable? 核酸检测结果出现误差）
     * 2 a person cannot be NORMAL and SUPER at the same time (alterable? 无症状感染者）
     * 3 a person cannot be NORMAL and INFECTED at the same time (alterable? 无症状感染者）
     * 4 a person cannot be SUPER and INFECTED at the same time (here INFECTED means those have been infected by virus but have not been super spreader)
     *
     * @throws Exception
     */
    public void stateCheck() throws Exception {
        Exception e = new RuntimeException("PersonStatesError");
        boolean a1 = this.isNORMAL();
        boolean a2 = this.isCONFIRMED();
        boolean a3 = this.isSUPER();
        boolean a4 = this.isINFECTED();

        if ((a1 && a2) || (a1 && a3) || (a1 && a4) || (a3 && a4)) {
            throw e;
        }

    }


    /**
     * one person move once
     *
     * @param range limits the distance of one time random walk of x,y , basically x ,y both >= 0
     * @param border
     */
    public void singleMove(int range, int border) {
        Random r = new Random();
        int x = this.coordinates.getX();
        int y = this.coordinates.getY();
        //todo : 对称 考虑边界%边界范围 check
        this.coordinates.setX(Math.abs(x += r.nextInt(range*2+1)-range)%border);
        this.coordinates.setY(Math.abs(y += r.nextInt(range*2+1)-range)%border);
    }

    /**
     * calculate the distance between THIS person and TARGET person
     *
     * @param people the TARGET person
     * @return distance of double type
     */
    public double calcDistance(People people) {
        double d;
        int xthis = this.coordinates.getX();
        int ythis = this.coordinates.getY();
        int xtarget = people.coordinates.getX();
        int ytarget = people.coordinates.getY();
        d = Math.sqrt(Math.abs(Math.pow((xthis - xtarget),2) + Math.pow((ythis - ytarget),2)));
        return d;
    }

    /**
     * @param p target person
     * @param d distance range(inclusive)
     * @return if this person is in the distance range from target person or not
     */
    public boolean isWithinDistance(People p, int d) {
        return this.calcDistance(p) <= d;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<StateEnum> getStates() {
        return states;
    }

//    public void setStates(List<StateEnum> states) {
//        this.states = states;
//    }

    public boolean isNORMAL() {
        return this.states.contains(StateEnum.NORMAL);
    }

    public boolean isCONTACTED() {
        return this.states.contains(StateEnum.CONTACTED);
    }

    public boolean isCONTACTEDS() {
        return this.states.contains(StateEnum.CONTACTEDS);
    }

    public boolean isCONFIRMED() {
        return this.states.contains(StateEnum.CONFIRMED);
    }

    public boolean isSUPER() {
        return this.states.contains(StateEnum.SUPER);
    }

    public boolean isQUARANTINED() {
        return this.states.contains(StateEnum.QUARANTINED);
    }

    public boolean isVACCINATED() {
        return this.states.contains(StateEnum.VACCINATED);
    }

    public boolean isMASK() {
        return this.states.contains(StateEnum.MASK);
    }

    public boolean isINFECTED() {
        return this.states.contains(StateEnum.INFECTED);
    }
}
