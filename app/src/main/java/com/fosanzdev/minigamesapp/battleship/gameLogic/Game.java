package com.fosanzdev.minigamesapp.battleship.gameLogic;

import com.fosanzdev.minigamesapp.battleship.boardLogic.Board;
import com.fosanzdev.minigamesapp.battleship.boardLogic.BoardBuilder;
import com.fosanzdev.minigamesapp.battleship.playerLogic.Player;

public class Game {

    private static final int BOARD_SIZE = 8;
    private static final int[] SHIPS = new int[] {2, 3, 3, 4, 5};

    private final Player player1;
    private final Player player2;

    private final Board board1;
    private final Board board2;

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
        boolean allSunk = false;
        while (!allSunk){
            if (turn){
                System.out.println("Player 1 turn");
                Hit hit = player1.getHit();
                board2.hit(hit);
                turn = false;
            }
            else {
                System.out.println("Player 2 turn");
                Hit hit = player2.getHit();
                board1.hit(hit);
                turn = true;
            }

            System.out.println("Player 1 board");
            board1.printBoard();
            System.out.println("Player 2 board");
            board2.printBoard();
        }
    }
}
