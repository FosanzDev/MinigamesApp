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
    public Board getBoard() {
        return board;
    }

    public void setTile(VTile tile, int x, int y){
        tiles[x][y] = tile;
    }
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

    public void visibilizeTile(int x, int y){
        enemyPOV[x][y].setResource(tiles[x][y].getVTileResource());
        enemyPOV[x][y].setOrientation(tiles[x][y].getOrientation());
    }
}
