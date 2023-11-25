package com.fosanzdev.minigamesapp.battleship.shipLogic;

public class ShipPart {

    private boolean hit = false;
    private VShip ship;

    public ShipPart(VShip ship) {
        this.ship = ship;
    }

    public boolean isHit() {
        return hit;
    }

    public void hit() {
        hit = true;
    }

    public VShip getShip() {
        return ship;
    }

    public void setShip(VShip ship) {
        this.ship = ship;
    }
}
