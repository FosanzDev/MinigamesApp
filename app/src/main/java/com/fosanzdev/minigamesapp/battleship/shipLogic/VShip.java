package com.fosanzdev.minigamesapp.battleship.shipLogic;

public class VShip{
    private final int area;
    private boolean sunk = false;
    private final ShipPart[] parts;
    private final Orientation orientation;
    public VShip(int area, Orientation orientation) {
        this.area = area;
        this.parts = new ShipPart[area];
        for (int i = 0; i < area; i++) {
            this.parts[i] = new ShipPart(this);
        }
        this.orientation = orientation;
    }

    public int getArea() {
        return area;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void notifyHit() {
        for (int i = 0; i < area; i++) {
            if (!parts[i].isHit()) {
                return;
            }
        }
        System.out.println("Ship sunk");
        sunk = true;
    }

    public ShipPart getPart(int i) {
        return parts[i];
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
