package com.fosanzdev.minigamesapp.battleship.playerLogic;

import com.fosanzdev.minigamesapp.battleship.gameLogic.Hit;

public abstract class Player {

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Hit getHit();

}
