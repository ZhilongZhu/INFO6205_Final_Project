package edu.neu.coe.info6205.simulation;

/**
 * mark the state of one person
 */
public enum StateEnum {
    NORMAL("Healthy",0),
    CONTACTED("ContactedWithPatients",1),
    CONTACTEDS("ContactedWithSuper",2),
    CONFIRMED("Infected",3),
    SUPER("SuperSpreader",4),
    QUARANTINED("Quarantined",5),
    VACCINATED("VaccineInjected",6),
    MASK("WearMask",7),
    INFECTED("InfectedByVirus",8);

    private final String name;
    private final int index;
    StateEnum(String name, int i) {
        this.name = name;
        this.index = i;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

}
