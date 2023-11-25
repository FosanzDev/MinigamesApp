package com.fosanzdev.minigamesapp.battleship;

public class BoardBuilder {

    public static Board buildRandomBoard(Ship[] ships, int sizeX, int sizeY) {
        Board board = new Board(sizeX, sizeY);
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int x = (int) (Math.random() * 10);
                int y = (int) (Math.random() * 10);
                Orientation orientation = Orientation.values()[(int) (Math.random() * 4)];
                VShip vShip = new VShip(ship, orientation, x, y);
                placed = board.addShip(vShip, x, y);
            }
        }
        return board;
    }
}
