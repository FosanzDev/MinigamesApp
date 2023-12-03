package com.fosanzdev.minigamesapp.fragmentos;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.fosanzdev.minigamesapp.R;

public class FragmentTicTacToe extends Fragment {

    public FragmentTicTacToe() {
        super(R.layout.fragment_tic_tac_toe);
    }
    private boolean playerOneActive;
    private int playerOneScore;
    private int computerScore;
    private TextView ScorePlayer;
    private TextView ScoreComputer;
    private TextView playerStatus;
    private ImageButton[] buttons;
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    private int rounds;

    //Metodo para crear la vista
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerOneActive = true;
        playerOneScore = 0;
        computerScore = 0;
        ScorePlayer = view.findViewById(R.id.tvScorePlayer);
        ScoreComputer = view.findViewById(R.id.tvScoreComputer);
        playerStatus = view.findViewById(R.id.tvPlayerStatus);

        rounds = 0;

        buttons = new ImageButton[9];
        buttons[0] = view.findViewById(R.id.btn1);
        buttons[1] = view.findViewById(R.id.btn2);
        buttons[2] = view.findViewById(R.id.btn3);
        buttons[3] = view.findViewById(R.id.btn4);
        buttons[4] = view.findViewById(R.id.btn5);
        buttons[5] = view.findViewById(R.id.btn6);
        buttons[6] = view.findViewById(R.id.btn7);
        buttons[7] = view.findViewById(R.id.btn8);
        buttons[8] = view.findViewById(R.id.btn9);

        for (int i = 0; i < buttons.length; i++) {
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCellClick(finalI);
                }
            });
        }

        Button btnReset = view.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                playerOneScore = 0;
                computerScore = 0;
                updatePlayerScore();
            }
        });

        Button btnPlayAgain = view.findViewById(R.id.btnPlayAgain);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }
    //Metodo para cuando se da click en una celda
    private void onCellClick(int index) {
        if (gameState[index] == 2) {
            ImageButton button = buttons[index];
            if (playerOneActive) {
                button.setBackgroundResource(R.drawable.tictactoe_x);
                gameState[index] = 0;
            } else {
                button.setBackgroundResource(R.drawable.tictactoe_0);
                gameState[index] = 1;
            }
            button.setEnabled(false);
            rounds++;

            if (checkWinner()) {
                if (playerOneActive) {
                    playerOneScore++;
                    playerStatus.setText("Player has won!");
                } else {
                    computerScore++;
                    playerStatus.setText("Computer has won!");
                }
                updatePlayerScore();
            } else if (rounds == 9) {
                playerStatus.setText("DRAW!");
            } else {
                playerOneActive = !playerOneActive;
            }
        }
    }
    //Metodo Para verificar si hay un ganador
    private boolean checkWinner() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    //Metodo para reiniciar el juego
    private void resetGame() {
        rounds = 0;
        playerOneActive = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setBackgroundResource(R.drawable.tictactoe_inicio);
            buttons[i].setEnabled(true);
        }
        playerStatus.setText("Status");
    }

    //Metodo para actualizar el marcador
    private void updatePlayerScore() {
        ScorePlayer.setText(String.valueOf(playerOneScore));
        ScoreComputer.setText(String.valueOf(computerScore));
    }
}
