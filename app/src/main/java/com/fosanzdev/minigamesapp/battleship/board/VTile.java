package com.fosanzdev.minigamesapp.battleship.board;

import com.fosanzdev.battleship.shipLogic.Orientation;

public class VTile {

    private VTileResource resource;
    private Orientation orientation;

    public VTile(VTileResource resource, Orientation orientation) {
        this.resource = resource;
        this.orientation = orientation;
    }

    public int getResource() {
        return resource.getResource();
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setResource(VTileResource resource) {
        this.resource = resource;
    }

    public void hit() {
        switch (resource) {
            case SHIP_NOSE:
                resource = VTileResource.SHIP_DAMAGED_NOSE;
                break;
            case SHIP_BODY_1:
                resource = VTileResource.SHIP_DAMAGED_BODY_1;
                break;
            case SHIP_BODY_2:
                resource = VTileResource.SHIP_DAMAGED_BODY_2;
                break;
            case SHIP_BACK:
                resource = VTileResource.SHIP_DAMAGED_BACK;
                break;
            case WATER:
            case UNKNOWN:
                resource = VTileResource.HIT;
                break;
        }
    }
}
