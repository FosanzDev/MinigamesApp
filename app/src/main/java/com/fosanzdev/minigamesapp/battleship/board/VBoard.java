package com.fosanzdev.minigamesapp.battleship.board;

import com.fosanzdev.battleship.boardLogic.Board;
import com.fosanzdev.battleship.gameLogic.Hit;

public class VBoard {

    private Board board;
    private VTile[][] tiles;
    private VTile[][] enemyPOV;


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

    public void setTile(VTile tile, int x, int y){
        tiles[x][y] = tile;
    }
    public void hit(Hit hit){
        tiles[hit.getX()][hit.getY()].hit();
        enemyPOV[hit.getX()][hit.getY()].hit();
    }
}
