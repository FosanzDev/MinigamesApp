package com.fosanzdev.minigamesapp.battleship.board;

import com.fosanzdev.battleship.boardLogic.Board;
import com.fosanzdev.battleship.boardLogic.Tile;
import com.fosanzdev.battleship.shipLogic.Orientation;
import com.fosanzdev.battleship.shipLogic.ShipPart;

public class VBoardBuilder {

    public static VBoard parseBoard(Board board){
        ShipPart[][] shipParts = board.getShipBoard();
        Tile[][] tiles = board.getTileBoard();

        VTile[][] vTiles = new VTile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[0].length; j++){
                if (shipParts[i][j] != null){
                    Orientation orientation = shipParts[i][j].getShip().getOrientation();
                    if (shipParts[i][j].getShipIndex() == 0){
                        //Ship back
                        vTiles[i][j] = new VTile(VTileResource.SHIP_BACK, orientation);
                    }
                    else if (shipParts[i][j].getShipIndex() == shipParts[i][j].getShip().getArea() - 1){
                        //Ship nose
                        vTiles[i][j] = new VTile(VTileResource.SHIP_NOSE, orientation);
                    }
                    else {
                        //Ship body
                        if (orientation == Orientation.N || orientation == Orientation.S)
                            vTiles[i][j] = i%2 == 0 ? new VTile(VTileResource.SHIP_BODY_1, orientation) : new VTile(VTileResource.SHIP_BODY_2, orientation);
                        else
                            vTiles[i][j] = j%2 == 0 ? new VTile(VTileResource.SHIP_BODY_1, orientation) : new VTile(VTileResource.SHIP_BODY_2, orientation);
                    }
                } else {
                    //Water
                    vTiles[i][j] = new VTile(VTileResource.WATER, Orientation.N);
                }
            }
        }

        VTile[][] vEnemyPOV = new VTile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[0].length; j++){
                    vEnemyPOV[i][j] = new VTile(VTileResource.WATER, Orientation.N);
            }
        }

        return new VBoard(vTiles, vEnemyPOV);
    }
}