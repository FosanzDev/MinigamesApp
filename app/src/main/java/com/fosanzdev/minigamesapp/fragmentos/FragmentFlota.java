package com.fosanzdev.minigamesapp.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.battleship.boardLogic.Board;
import com.fosanzdev.battleship.boardLogic.BoardBuilder;
import com.fosanzdev.minigamesapp.R;

import com.fosanzdev.minigamesapp.battleship.adapters.FlotaAdapter;
import com.fosanzdev.minigamesapp.battleship.board.VBoard;
import com.fosanzdev.minigamesapp.battleship.board.VBoardBuilder;
import com.fosanzdev.minigamesapp.battleship.game.Game;
import com.fosanzdev.minigamesapp.battleship.game.HumanPlayer;
import com.fosanzdev.minigamesapp.battleship.game.Player;

import kotlinx.coroutines.scheduling.Task;

public class FragmentFlota extends Fragment implements Game.GameListener, FlotaAdapter.OnTileClickListener {
    private static int[] ships = {5, 4, 3, 3, 2};
    private static int BOARD_SIZE_X = 10;
    private static int BOARD_SIZE_Y = 10;

    Player player;
    Player cpu;
    Game game;

    TextView tvNowPlaying;
    Button bFire;
    Button bRestart;

    View.OnClickListener fireListener;
    View.OnClickListener restartListener;

    public FragmentFlota() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flota, container, false);

        player = new HumanPlayer("Esteban");
        cpu = new HumanPlayer("CPU");

        //Setting up player board
        Board playerBoard = BoardBuilder.buildRandomBoard(ships, BOARD_SIZE_X, BOARD_SIZE_Y);
        player.setvBoard(VBoardBuilder.parseBoard(playerBoard));

        //Setting up CPU board
        Board cpuBoard = BoardBuilder.buildRandomBoard(ships, BOARD_SIZE_X, BOARD_SIZE_Y);
        cpu.setvBoard(VBoardBuilder.parseBoard(cpuBoard));

        //Setting up RecyclerView for player board
        RecyclerView rvBoard = v.findViewById(R.id.rvBoard);
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvBoard.setAdapter(new FlotaAdapter(player.getvBoard(), getContext(), rvBoard, this));
        rvBoard.setHasFixedSize(true);

        //Setting up Ids
        tvNowPlaying = v.findViewById(R.id.tvPlaying);
        bFire = v.findViewById(R.id.bFire);
        bRestart = v.findViewById(R.id.bRestart);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        game = new Game(this, player, cpu);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onGameCreated() {
        bFire.setText("Start");

        Thread t = new Thread(() -> {
            try {
                tvNowPlaying.post(() -> tvNowPlaying.setText("Hello there hooman!"));
                Thread.sleep(2000);
                tvNowPlaying.post(() -> tvNowPlaying.setText("I'm your opponent!"));
                Thread.sleep(2000);
                tvNowPlaying.post(() -> tvNowPlaying.setText("I'm going to destroy you!"));
                Thread.sleep(2500);
                tvNowPlaying.post(() -> tvNowPlaying.setText("This board is your board!"));
                Thread.sleep(2500);
                tvNowPlaying.post(() -> tvNowPlaying.setText("Take a look at it!"));
                Thread.sleep(2500);
                tvNowPlaying.post(() -> tvNowPlaying.setText("Whenever you're ready, press the Start button!"));
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                return;
            }
        });

        t.start();

        bFire.setOnClickListener(v -> {
            bFire.setText("Fire!");
            t.interrupt();
            game.start();
        });
    }

    @Override
    public void onGameStart() {
        bFire.setOnClickListener(v -> {
            //TODO: Fire logic
        });
        tvNowPlaying.setText("Your turn!");
    }

    @Override
    public void onGameEnd() {

    }

    @Override
    public void onTurnChange(Player comingPlayer) {

    }

    @Override
    public void onTileClick(int x, int y) {
        System.out.println("Clicked on: " + x + ", " + y);
    }
}
