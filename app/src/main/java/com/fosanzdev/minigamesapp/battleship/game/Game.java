package com.fosanzdev.minigamesapp.battleship.game;

import com.fosanzdev.battleship.gameLogic.Hit;

public class Game {

    public interface GameListener{
        void onGameCreated();
        void onGameStart();
        void onGameEnd();
        void onTurnChange(Player comingPlayer);
        void onHit(Hit hit);
    }

    private final GameListener listener;
    private final Player player1;
    private final Player player2;

    private Player currentPlayer;

    public Game(GameListener listener, Player player1, Player player2){
        this.listener = listener;
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player2;
        listener.onGameCreated();
    }

    public void start(){
        listener.onGameStart();
    }

    public void manageHit(Hit hit, boolean isPlayer){
        if (isPlayer)
            player1.hit(hit);
        else
            player2.hit(hit);

        listener.onHit(hit);
    }
}
