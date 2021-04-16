package edu.neu.coe.info6205.util;

import java.util.Random;

public class CoordinateGenerator {
    private Random r;

    public CoordinateGenerator() {
        this.r = new Random();
    }

    /**
     * randomly generate x y coordinate pair, uniform distribution
     * @param bound exclusive higher bound of the random generator [0,bound)
     * @return new coordinate
     */
    public Coordinates generateRandomCoordinates(int bound){
        int x = r.nextInt(bound);
        int y = r.nextInt(bound);
        Coordinates coordinates = new Coordinates(x,y);
        return coordinates;
    }
}
