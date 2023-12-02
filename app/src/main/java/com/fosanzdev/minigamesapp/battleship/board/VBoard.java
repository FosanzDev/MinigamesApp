package com.fosanzdev.minigamesapp.battleship.board;

public class VBoard {

    private VTile[][] tiles;
    private VTile[][] enemyPOV;


    public VBoard(VTile[][] tiles, VTile[][] enemyPOV){
        this.tiles = tiles;
        this.enemyPOV = enemyPOV;
    }

    public VTile[][] getTiles() {
        return tiles;
    }

    public void setTile(VTile tile, int x, int y){
        tiles[x][y] = tile;
    }
}
