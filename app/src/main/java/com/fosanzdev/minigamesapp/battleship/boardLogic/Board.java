package com.fosanzdev.minigamesapp.battleship.boardLogic;

import com.fosanzdev.minigamesapp.battleship.gameLogic.Hit;
import com.fosanzdev.minigamesapp.battleship.shipLogic.Orientation;
import com.fosanzdev.minigamesapp.battleship.shipLogic.ShipPart;
import com.fosanzdev.minigamesapp.battleship.shipLogic.VShip;

import java.util.ArrayList;

public class Board{

    //List all the ships
    private ArrayList<VShip> ships;

    //List all the ships in the board
    private ShipPart[][] shipBoard;

    //List all the tiles/hits in the board (water, ship, hit, miss)
    private Tile[][] tileBoard;

    public Board(int xSize, int ySize) {
        this.ships = new ArrayList<>();
        this.shipBoard = new ShipPart[xSize][ySize];
        this.tileBoard = new Tile[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize ; j++) {
                tileBoard[i][j] = Tile.WATER;
            }
        }
    }

    /**
     * Adds a ship to the board.
     * This adds the reference of the object to the collisionBoard.
     * CollisionBoard is a matrix of VShip objects.
     * If the position is already occupied, it returns false.
     * @param vship Ship to be added
     * @param x X coordinate
     * @param y Y coordinate
     */
    public boolean addShip(VShip vship, int x, int y) {
        //Add the ship to the list of ships
        ships.add(vship);

        //Check if the ship can be placed
        if (checkCollision(vship, x, y)) {

            //if it can, add it to the collisionBoard
            int area = vship.getArea();
            Orientation orientation = vship.getOrientation();
            if (orientation == Orientation.E) {
                for (int i = 0; i < area; i++) {
                    shipBoard[x][y + i] = vship.getPart(i);
                    tileBoard[x][y + i] = Tile.SHIP;
                }
            }
            else if (orientation == Orientation.W) {
                for (int i = 0; i < area; i++) {
                    shipBoard[x][y - i] = vship.getPart(i);
                    tileBoard[x][y - i] = Tile.SHIP;
                }
            }
            else if (orientation == Orientation.N) {
                for (int i = 0; i < area; i++) {
                    shipBoard[x - i][y] = vship.getPart(i);
                    tileBoard[x - i][y] = Tile.SHIP;
                }
            }
            else if (orientation == Orientation.S) {
                for (int i = 0; i < area; i++) {
                    shipBoard[x + i][y] = vship.getPart(i);
                    tileBoard[x + i][y] = Tile.SHIP;
                }
            }
            return true;
        }

        return false;
    }

    /**
     * Returns whenever a ship can be placed in the given position
     * @param vship Ship to be placed
     * @param x X coordinate
     * @param y Y coordinate
     * @return True if the ship can be placed, false otherwise
     */
    public boolean checkCollision(VShip vship, int x, int y) {
        //Get area and orientation of the ship
        int area = vship.getArea();
        Orientation orientation = vship.getOrientation();

        //Check if it collides with another ship
        try{
            //If its orientation is E or W, check the Y axis
            if (orientation == Orientation.E) {
                for (int i = 0; i < area; i++) {
                    if (shipBoard[x][y + i] != null) {
                        return false;
                    }
                }
            }
            else if (orientation == Orientation.W) {
                for (int i = 0; i < area; i++) {
                    if (shipBoard[x][y - i] != null) {
                        return false;
                    }
                }
            }

            //If its orientation is N or S, check the X axis
            else if (orientation == Orientation.N) {
                for (int i = 0; i < area; i++) {
                    if (shipBoard[x - i][y] != null) {
                        return false;
                    }
                }
            }
            else if (orientation == Orientation.S) {
                for (int i = 0; i < area; i++) {
                    if (shipBoard[x + i][y] != null) {
                        return false;
                    }
                }
            }
            return true;

            //If this error is thrown, the ship is out of bounds (collides with the borders)
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean hit(Hit hit) {
        Tile tile = tileBoard[hit.getX()][hit.getY()];
        if (tile == Tile.SHIP) {
            //Identify the ship
            ShipPart part = shipBoard[hit.getX()][hit.getY()];
            VShip ship = part.getShip();

            //Hit the ship
            shipBoard[hit.getX()][hit.getY()].hit();

            //Check if the ship is sunk
            if (ship.isSunk()) {
                ships.remove(ship);
            }

            //Change the tile to HIT
            tileBoard[hit.getX()][hit.getY()] = Tile.HIT;
            return true;
        }
        else if (tile == Tile.WATER) {
            tileBoard[hit.getX()][hit.getY()] = Tile.MISS;
            return false;
        }
        return false;
    }


    public boolean allSunk(){
        return ships.isEmpty();
    }

    public void printBoard(){
        for (int i = 0; i < tileBoard.length; i++) {
            for (int j = 0; j < tileBoard[i].length; j++) {
                switch (tileBoard[i][j]) {
                    case WATER:
                        System.out.print("~ ");
                        break;
                    case SHIP:
                        System.out.print("S ");
                        break;
                    case HIT:
                        System.out.print("X ");
                        break;
                    case MISS:
                        System.out.print("O ");
                        break;
                }
            }
            System.out.println();
        }
    }
}

