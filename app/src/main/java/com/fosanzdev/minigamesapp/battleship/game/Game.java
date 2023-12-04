package com.fosanzdev.minigamesapp.battleship.game;

import com.fosanzdev.battleship.boardLogic.Board;
import com.fosanzdev.battleship.gameLogic.Hit;

public class Game implements Board.BoardListener {
    /*This class is an interface between the game logic and the UI. It will notify the UI of the events
    * To see the game logic, check https://github.com/fosanzdev/Battleship
    */

    //Listener of the game events
    public interface GameListener{
        void onGameCreated();
        void onGameStart();
        void onGameEnd();
        void onTurnChange(Player comingPlayer);
        void onHit();
        void onMiss();
        void onShipHit();
        void onInvalidHit();
        void onShipSunk();
        Player getNowPlaying();
    }

    //The entity that will listen to the game events
    private final GameListener listener;

    //The two players
    private final Player player1;
    private final Player player2;

    //A flag to know if all the ships are sunk (necessary to end the game and notify after a hit is done)
    private boolean allSunk = false;

    public Game(GameListener listener, Player player1, Player player2){
        this.listener = listener;
        this.player1 = player1;
        this.player2 = player2;

        //set the listener of the boards since this class implements the BoardListener interface
        player1.getvBoard().getBoard().setListener(this);
        player2.getvBoard().getBoard().setListener(this);

        //Inmediately notify the listener that the game is created and ready to start
        this.listener.onGameCreated();
    }

    /**
     * Start the game
     */
    public void start(){
        listener.onGameStart();
    }

    /**
     * Notify the UI if a ship was hit
     * @param hit (Not used) Hit that was performed
     */
    @Override
    public void onHit(Hit hit) {
        listener.onShipHit();
    }

    /**
     * Notify the UI if a ship was not hit (missed)
     * @param hit (Not used) Hit that was performed
     */
    @Override
    public void onMiss(Hit hit) {
        listener.onMiss();
    }

    /**
     * Notify the UI if a ship was sunk
     * @param hits Array of hits that indicate the coordinates of the sunk ship
     */
    @Override
    public void onSunk(Hit[] hits) {
        Player player = listener.getNowPlaying();
        listener.onShipSunk();
        player = player == player1 ? player2 : player1;
        //Visibilize the board of the player that sunk the ship
        for (Hit hit : hits){
            player.getvBoard().visibilizeTile(hit.getX(), hit.getY());
        }
    }

    /**
     * Set the flag to know if all the ships are sunk
     */
    @Override
    public void onAllSunk() {
        allSunk = true;
    }

    /**
     * Manage the hit. This is used by the UI to notify the game that a hit was performed
     * @param hit Hit that was performed
     * @param player Player that performed the hit
     */
    public void manageHit(Hit hit, Player player){
        //Check if the hit is not repeated
        boolean valid;

        //Get the player that is going to be hit
        Player toHit = player == player1 ? player2 : player1;
        //Hit the player with the given coordinates
        valid = toHit.hit(hit);

        //If the hit was invalid (already hit), notify the UI
        if (!valid){
            listener.onInvalidHit();
        }

        //If the hit was valid, notify the UI and check if all the ships are sunk
        else
            listener.onHit();

        //If they are sunk, notify the UI for the game to end
        if (allSunk)
            listener.onGameEnd();

    }
}
