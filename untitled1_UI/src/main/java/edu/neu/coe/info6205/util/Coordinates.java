package edu.neu.coe.info6205.util;

public class Coordinates {
    private int x;//x coordinate
    private int y;//y coordinate

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * increase x,y coordinate by the input x,y unit
     * @param x
     * @param y
     */
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
