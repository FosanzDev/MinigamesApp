package com.fosanzdev.minigamesapp.battleship.board;

import com.fosanzdev.battleship.shipLogic.Orientation;

public class VTile {

    private VTileResource resource;
    private Orientation orientation;

    public VTile(VTileResource resource, Orientation orientation){
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
}
