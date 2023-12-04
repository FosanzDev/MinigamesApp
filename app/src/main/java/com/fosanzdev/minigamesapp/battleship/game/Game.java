package com.fosanzdev.minigamesapp.battleship.game;

import com.fosanzdev.battleship.boardLogic.Board;
import com.fosanzdev.battleship.gameLogic.Hit;
import com.fosanzdev.battleship.shipLogic.ShipPart;
import com.fosanzdev.battleship.shipLogic.VShip;
import com.fosanzdev.minigamesapp.battleship.board.VBoard;

public class Game implements Board.BoardListener {

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

    private final GameListener listener;
    private final Player player1;
    private final Player player2;
    private boolean allSunk = false;

    public Game(GameListener listener, Player player1, Player player2){
        this.listener = listener;
        this.player1 = player1;
        this.player2 = player2;

        player1.getvBoard().getBoard().setListener(this);
        player2.getvBoard().getBoard().setListener(this);
        this.listener.onGameCreated();
    }

    public void start(){
        listener.onGameStart();
    }


    @Override
    public void onHit(Hit hit) {
        listener.onShipHit();
    }

    @Override
    public void onMiss(Hit hit) {
        listener.onMiss();
    }

    @Override
    public void onSunk(Hit[] hits) {
        Player player = listener.getNowPlaying();
        listener.onShipSunk();
        player = player == player1 ? player2 : player1;
        for (Hit hit : hits){
            player.getvBoard().visibilizeTile(hit.getX(), hit.getY());
        }
    }

    @Override
    public void onAllSunk() {
        allSunk = true;
    }

    public void manageHit(Hit hit, Player player){
        //Check if the hit is not repeated
        boolean valid;

        Player toHit = player == player1 ? player2 : player1;
        valid = toHit.hit(hit);

        if (!valid){
            listener.onInvalidHit();
        }
        else
            listener.onHit();

        if (allSunk)
            listener.onGameEnd();

    }
}
