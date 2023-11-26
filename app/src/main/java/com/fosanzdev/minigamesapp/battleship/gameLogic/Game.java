package com.fosanzdev.minigamesapp.battleship.gameLogic;

import com.fosanzdev.minigamesapp.battleship.boardLogic.Board;
import com.fosanzdev.minigamesapp.battleship.boardLogic.BoardBuilder;
import com.fosanzdev.minigamesapp.battleship.playerLogic.Player;

public class Game {

    private static final int BOARD_SIZE = 8;
    private static final int[] SHIPS = new int[] {2, 3, 3, 4, 5};

    private Player player1;
    private Player player2;

    private Board board1;
    private Board board2;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        board1 = BoardBuilder.buildRandomBoard(SHIPS, BOARD_SIZE, BOARD_SIZE);
        board2 = BoardBuilder.buildRandomBoard(SHIPS, BOARD_SIZE, BOARD_SIZE);
    }

    public Game(Player player1, Player player2, Board board1, Board board2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board1 = board1;
        this.board2 = board2;
    }

    public void play() {
        //Turn defines which one is playing
        //If it's true, player1 is playing
        boolean turn = true;
        //allSunk defines if all ships are sunk
        boolean allSunk = false;
        while (!allSunk){
            //If it's player1 turn, player1 hits player2 board
            if (turn){
                System.out.println("Player 1 turn");
                //Get hit from player1
                Hit hit = player1.getHit();
                //Hit player2 board
                board2.hit(hit);
                //Check if all ships are sunk
                allSunk = board2.allSunk();
                //Change turn
                turn = false;
            }
            else {
                System.out.println("Player 2 turn");
                Hit hit = player2.getHit();
                board1.hit(hit);
                allSunk = board1.allSunk();
                turn = true;
            }

            System.out.println("Player 1 board");
            board1.printBoard();
            System.out.println("Player 2 board");
            board2.printBoard();
        }

        if (turn){
            System.out.println("Player 1 won!");
        }
        else {
            System.out.println("Player 2 won!");
        }
    }

}

