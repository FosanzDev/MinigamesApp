package com.fosanzdev.minigamesapp.battleship.shipLogic;

public class VShip {

    private final int area;
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

    public ShipPart getPart(int i) {
        return parts[i];
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
