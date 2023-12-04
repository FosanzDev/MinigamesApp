package com.fosanzdev.minigamesapp.battleship.game;

import com.fosanzdev.battleship.gameLogic.Hit;
import com.fosanzdev.minigamesapp.battleship.board.VTile;
import com.fosanzdev.minigamesapp.battleship.board.VTileResource;

public class CpuPlayer extends Player{

    //CPUPlayer has a visible board to know where to hit. This is set by the game and
    // is the VBoard enemyPOV (so the CPUPlayer can't see the ships)
    VTile[][] visibleBoard;

    public CpuPlayer(String name) {
        super(name);
    }
    public void setVisibleBoard(VTile[][] visibleBoard){
        this.visibleBoard = visibleBoard;
    }

    /**
     * Logic to play the turn. This is a simple random hit but can implement more complex logic
     * @return Hit to be performed
     */
    public Hit play(){
        while (true){
            //Get a random tile from the board
            int x = (int) (Math.random() * visibleBoard.length);
            int y = (int) (Math.random() * visibleBoard[0].length);

            //Repeat until the tile is not already hit
            if (visibleBoard[x][y].getResource() == VTileResource.UNKNOWN.getResource()){
                //Return the hit
                return new Hit(x, y);
            }
        }
    }
}
