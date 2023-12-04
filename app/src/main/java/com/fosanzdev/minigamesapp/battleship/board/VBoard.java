package com.fosanzdev.minigamesapp.battleship.board;

import com.fosanzdev.battleship.boardLogic.Board;
import com.fosanzdev.battleship.gameLogic.Hit;

public class VBoard {

    //Save the board and the tiles (visible and enemyPOV)
    private final Board board;
    private final VTile[][] tiles;
    private final VTile[][] enemyPOV;


    /**
     * Constructor
     * Separating between the tiles and the enemyPOV is an effective way to switch beetween
     * the friendly POV and the enemy POV without having to calculate the enemy POV every time
     *
     * @param board original board
     * @param tiles VTiles to be displayed to the friendly POV
     * @param enemyPOV VTiles to be displayed in the enemy POV
     */
    public VBoard(Board board, VTile[][] tiles, VTile[][] enemyPOV){
        this.board = board;
        this.tiles = tiles;
        this.enemyPOV = enemyPOV;
    }

    public VTile[][] getTiles() {
        return tiles;
    }
    public VTile[][] getEnemyPOV() {
        return enemyPOV;
    }
    public Board getBoard() {
        return board;
    }

    public void setTile(VTile tile, int x, int y){
        tiles[x][y] = tile;
    }

    /**
     * Hit a tile in the board. This affects the enemyPOV, the friendly POV and the original board
     * Hierarchy: enemyPOV -> friendly POV -> original board
     * If the hierarchy is not followed, the game will have erratic behaviour
     *
     * enemyPOV first: if the normalHit is done, enemyPOV can't know wether the tile was hit or not
     * board last: this will make the listeners to be called on last instance, so the game can respond
     * to visual changes first, then on the game logic itself
     *
     * @param hit Hit to be performed
     * @return true if the hit was valid, false otherwise, taking valid as if the position can be hit (not already hit)
     */
    public boolean hit(Hit hit){
        VTile visibleTile = tiles[hit.getX()][hit.getY()];
        if (enemyPOV[hit.getX()][hit.getY()].hiddenHit(visibleTile)){
            visibleTile.normalHit();
            board.hit(hit);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Visibilize a tile in the enemyPOV
     * This function is used when a ship is sunk, so the enemy can see the ship that was sunk
     * Is as simple as changing the resource of the enemyPOV (hit) to the resource of the tile (ship)
     *
     * @param x x position of the tile
     * @param y y position of the tile
     */
    public void visibilizeTile(int x, int y){
        enemyPOV[x][y].setResource(tiles[x][y].getVTileResource());
        enemyPOV[x][y].setOrientation(tiles[x][y].getOrientation());
    }
}
