package com.fosanzdev.minigamesapp.battleship.board;

import com.fosanzdev.battleship.boardLogic.Board;
import com.fosanzdev.battleship.boardLogic.Tile;
import com.fosanzdev.battleship.shipLogic.Orientation;
import com.fosanzdev.battleship.shipLogic.ShipPart;

public class VBoardBuilder {

    /**
     * Parse a Board object to a VBoard object
     *
     * This method is used to create the VBoard. VBoard is a visual representation of the board
     * @param board Board to be parsed
     * @return parsed VBoard
     *
     * @see com.fosanzdev.battleship.boardLogic.Board Board (to understand the board object and its structure)
     * @see VTileResource VTileResource (to understand what the VTileResource is and how it is used)
     */
    public static VBoard parseBoard(Board board){
        //Get where the ships are
        ShipPart[][] shipParts = board.getShipBoard();

        //Get where the tiles (water, ship, hit) are
        //This is used to know which tiles were hit
        Tile[][] tiles = board.getTileBoard();

        VTile[][] vTiles = new VTile[tiles.length][tiles[0].length];

        //Run over the board
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[0].length; j++){
                if (shipParts[i][j] != null){
                    //If there is a shipPart, it means there is a ship
                    //Get its orientation
                    Orientation orientation = shipParts[i][j].getShip().getOrientation();

                    //If the shipPart is the first or the last, it means it is the nose or the back
                    //If the shipPart is not the first or the last, it means it is the body
                    //Also check if any of the parts is hit (necessary to play an already started game)
                    if (shipParts[i][j].getShipIndex() == 0){
                        //Check if shipPart is hit
                        if (shipParts[i][j].isHit())
                            //Ship back hit
                            vTiles[i][j] = new VTile(VTileResource.SHIP_DAMAGED_BACK, orientation);
                        else
                            //Ship back
                            vTiles[i][j] = new VTile(VTileResource.SHIP_BACK, orientation);
                    }
                    else if (shipParts[i][j].getShipIndex() == shipParts[i][j].getShip().getArea() - 1){
                        //Check if shipPart is hit
                        if (shipParts[i][j].isHit())
                            //Ship nose hit
                            vTiles[i][j] = new VTile(VTileResource.SHIP_DAMAGED_NOSE, orientation);
                        else
                            //Ship nose
                            vTiles[i][j] = new VTile(VTileResource.SHIP_NOSE, orientation);
                    }
                    else {
                        //Check if shipPart is hit
                        if (shipParts[i][j].isHit()){
                            //Ship body hit
                            if (orientation == Orientation.N || orientation == Orientation.S)
                                vTiles[i][j] = i%2 == 0 ? new VTile(VTileResource.SHIP_DAMAGED_BODY_1, orientation) : new VTile(VTileResource.SHIP_DAMAGED_BODY_2, orientation);
                            else
                                vTiles[i][j] = j%2 == 0 ? new VTile(VTileResource.SHIP_DAMAGED_BODY_1, orientation) : new VTile(VTileResource.SHIP_DAMAGED_BODY_2, orientation);

                        } else {
                            if (orientation == Orientation.N || orientation == Orientation.S)
                                vTiles[i][j] = i%2 == 0 ? new VTile(VTileResource.SHIP_BODY_1, orientation) : new VTile(VTileResource.SHIP_BODY_2, orientation);
                            else
                                vTiles[i][j] = j%2 == 0 ? new VTile(VTileResource.SHIP_BODY_1, orientation) : new VTile(VTileResource.SHIP_BODY_2, orientation);
                        }
                    }
                } else {
                    //Water
                    vTiles[i][j] = new VTile(VTileResource.WATER, Orientation.N);
                }
            }
        }

        //Create the enemyPOV with all tiles unknown
        //This needs to be changed in order to load an already started game
        VTile[][] vEnemyPOV = new VTile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[0].length; j++){
                    vEnemyPOV[i][j] = new VTile(VTileResource.UNKNOWN, Orientation.N);
            }
        }

        //Return the VBoard
        return new VBoard(board, vTiles, vEnemyPOV);
    }
}
