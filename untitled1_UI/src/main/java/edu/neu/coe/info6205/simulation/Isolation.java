package edu.neu.coe.info6205.simulation;

import edu.neu.coe.info6205.util.Day;

import java.util.*;

public class Isolation {
    //private Day day;
    private HashMap<People, Integer> peopleIsolatedHash;

    public Isolation() {
        //this.day = d;
        this.peopleIsolatedHash = new HashMap<>();
    }


    public HashMap<People, Integer> getPeopleIsolatedHash() {
        return peopleIsolatedHash;
    }

    /**
     * remove all states of the person p
     * then add QUARANTINED to p
     * put p into HashMap
     * @param p
     */
    public void addToIsolation(People p) {
//        p.removeAllState();
        p.addState(StateEnum.QUARANTINED);
        //todo 避免重复 check
        if(!this.peopleIsolatedHash.containsKey(p)){
            this.peopleIsolatedHash.put(p, 0);
        }

    }

    /**
     * update the isolation day of all people in Isolation by increasing 1
     */
    public void updateIsolationDay() {
        for (Map.Entry<People, Integer> e :
                this.peopleIsolatedHash.entrySet()) {
            People p = e.getKey();
            int i = e.getValue() + 1;
            this.peopleIsolatedHash.put(p, i);

        }
    }

    /**
     * return list of people who have been isolated >= 14 days
     * shift the state of those people from QUARANTINED to NORMAL
     * @return list of people
     */
    public List<People> getPeopleOut() {
        List<People> backToNormal = new ArrayList<>();
        for (Iterator<People> it = this.peopleIsolatedHash.keySet().iterator();it.hasNext();) {
            People p = it.next();
            int i = this.peopleIsolatedHash.get(p);
            if (i >= 14) {
                //p.getStates().remove(StateEnum.QUARANTINED);
//                p.becomeNormal();
                backToNormal.add(p);
                it.remove();
            }
        }
        return backToNormal;
    }
}
