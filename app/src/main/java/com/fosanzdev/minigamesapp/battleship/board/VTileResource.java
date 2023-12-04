package com.fosanzdev.minigamesapp.battleship.board;

import com.fosanzdev.minigamesapp.R;

/**
 * Enum with all the resources used in the board
 */
public enum VTileResource {

    //General
    WATER(R.drawable.battleship_water),
    UNKNOWN(R.drawable.battleship_unknown),
    HIT(R.drawable.battleship_damaged),

    //Ship (Not using the horizontal ones since they will get rotated)
    SHIP_NOSE(R.drawable.battleship_0_vertical),
    SHIP_BODY_1(R.drawable.battleship_1_vertical),
    SHIP_BODY_2(R.drawable.battleship_2_vertical),
    SHIP_BACK(R.drawable.battleship_3_vertical),

    //Ship damaged (Not using the horizontal ones since they will get rotated)
    SHIP_DAMAGED_NOSE(R.drawable.battleship_0_damaged_vertical),
    SHIP_DAMAGED_BODY_1(R.drawable.battleship_1_damaged_vertical),
    SHIP_DAMAGED_BODY_2(R.drawable.battleship_2_damaged_vertical),
    SHIP_DAMAGED_BACK(R.drawable.battleship_3_damaged_vertical);

    private final int resource;

    VTileResource(int resource){
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }
}
