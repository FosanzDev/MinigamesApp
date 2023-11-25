package com.fosanzdev.minigamesapp.battleship.boardLogic;

import com.fosanzdev.minigamesapp.battleship.shipLogic.Orientation;
import com.fosanzdev.minigamesapp.battleship.shipLogic.VShip;

public class BoardBuilder {

    public static Board buildRandomBoard(int[] ships, int sizeX, int sizeY) {
        Board board = new Board(sizeX, sizeY);
        for (int area : ships) {
            boolean placed = false;
            while (!placed) {
                int x = (int) (Math.random() * 10);
                int y = (int) (Math.random() * 10);
                Orientation orientation = Orientation.values()[(int) (Math.random() * 4)];
                VShip vShip = new VShip(area, orientation);
                placed = board.addShip(vShip, x, y);
            }
        }
        return board;
    }
}
