package com.fosanzdev.minigamesapp.battleship.gameLogic;

public class Hit {
    private final int x;
    private final int y;

    public Hit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
