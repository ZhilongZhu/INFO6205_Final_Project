package edu.neu.coe.info6205.simulation;

import edu.neu.coe.info6205.util.Day;

import java.util.HashMap;

public class NonIsolation {
//    private Day day;
    private HashMap<People,Integer> peopleNonIsoHash;

    public NonIsolation() {
//        this.day = day;
        this.peopleNonIsoHash = new HashMap<>();
    }

    public HashMap<People, Integer> getPeopleNonIsoHash() {
        return peopleNonIsoHash;
    }

    public void addToNonIso(People p){
        this.peopleNonIsoHash.put(p,0);
    }





}
