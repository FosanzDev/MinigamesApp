package com.fosanzdev.minigamesapp.battleship;

public class Board {
    private VShip[][] collisionBoard;

    public Board(int xSize, int ySize) {
        this.collisionBoard = new VShip[xSize][ySize];
    }

    public VShip[][] getCollisionBoard() {
        return collisionBoard;
    }

    public void setCollisionBoard(VShip[][] collisionBoard) {
        this.collisionBoard = collisionBoard;
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
        //Check if the ship can be placed
        if (checkCollision(vship, x, y)) {

            //if it can, add it to the collisionBoard
            int area = vship.getShip().getArea();
            Orientation orientation = vship.getOrientation();
            if (orientation == Orientation.E) {
                for (int i = 0; i < area; i++) {
                    collisionBoard[x][y + i] = vship;
                }
            }
            else if (orientation == Orientation.W) {
                for (int i = 0; i < area; i++) {
                    collisionBoard[x][y - i] = vship;
                }
            }
            else if (orientation == Orientation.N) {
                for (int i = 0; i < area; i++) {
                    collisionBoard[x - i][y] = vship;
                }
            }
            else if (orientation == Orientation.S) {
                for (int i = 0; i < area; i++) {
                    collisionBoard[x + i][y] = vship;
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
        int area = vship.getShip().getArea();
        Orientation orientation = vship.getOrientation();

        //Check if it collides with another ship
        try{
            //If its orientation is E or W, check the Y axis
            if (orientation == Orientation.E) {
                for (int i = 0; i < area; i++) {
                    if (collisionBoard[x][y + i] != null) {
                        return false;
                    }
                }
            }
            else if (orientation == Orientation.W) {
                for (int i = 0; i < area; i++) {
                    if (collisionBoard[x][y - i] != null) {
                        return false;
                    }
                }
            }

            //If its orientation is N or S, check the X axis
            else if (orientation == Orientation.N) {
                for (int i = 0; i < area; i++) {
                    if (collisionBoard[x - i][y] != null) {
                        return false;
                    }
                }
            }
            else if (orientation == Orientation.S) {
                for (int i = 0; i < area; i++) {
                    if (collisionBoard[x + i][y] != null) {
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
}
