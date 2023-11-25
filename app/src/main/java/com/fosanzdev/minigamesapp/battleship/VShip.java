package com.fosanzdev.minigamesapp.battleship;

public class VShip {

    private Ship ship;
    private Orientation orientation;
    private int x;
    private int y;
    private int hits = 0;

    public VShip(Ship ship, Orientation orientation, int x, int y) {
        this.ship = ship;
        this.orientation = orientation;
        this.x = x;
        this.y = y;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
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

    public void hit() {
        hits++;
    }

    public boolean isSunk() {
        return hits == ship.getArea();
    }
}
