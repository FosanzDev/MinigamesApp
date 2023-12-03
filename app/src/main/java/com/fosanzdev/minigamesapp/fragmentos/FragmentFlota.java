package com.fosanzdev.minigamesapp.fragmentos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.battleship.boardLogic.Board;
import com.fosanzdev.battleship.boardLogic.BoardBuilder;
import com.fosanzdev.battleship.gameLogic.Hit;
import com.fosanzdev.minigamesapp.R;

import com.fosanzdev.minigamesapp.battleship.adapters.FlotaAdapter;
import com.fosanzdev.minigamesapp.battleship.board.VBoardBuilder;
import com.fosanzdev.minigamesapp.battleship.game.Game;
import com.fosanzdev.minigamesapp.battleship.game.HumanPlayer;
import com.fosanzdev.minigamesapp.battleship.game.Player;

public class FragmentFlota extends Fragment implements Game.GameListener, FlotaAdapter.OnTileClickListener {
    private static int[] ships = {5, 4, 3, 3, 2};
    private static int BOARD_SIZE_X = 10;
    private static int BOARD_SIZE_Y = 10;

    Player player;
    Player cpu;
    Player nowPlaying;

    Game game;
    Hit selectedTile;
    ImageView ivSelectedTile;
    FlotaAdapter adapter;

    TextView tvNowPlaying;
    Button bFire;
    Button bRestart;
    RecyclerView rvBoard;

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
        rvBoard = v.findViewById(R.id.rvBoard);
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        adapter = new FlotaAdapter(player.getvBoard(), getContext(), rvBoard, this);
        rvBoard.setAdapter(adapter);
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
        if (nowPlaying == player) {
            tvNowPlaying.setText("Your turn!");
        }
        else {
            tvNowPlaying.setText("CPU's turn!");
            adapter.updateBoard(cpu.getvBoard(), false);
        }

        bFire.setOnClickListener(v -> {
            if (nowPlaying == player) {
                onTurnChange(cpu);
            } else
                onTurnChange(player);
        });
        tvNowPlaying.setText("Your turn!");
    }

    @Override
    public void onGameEnd() {

    }

    @Override
    public void onTurnChange(Player comingPlayer) {
        if (ivSelectedTile != null) removeFilters(ivSelectedTile);
        ivSelectedTile = null;
        selectedTile = null;
        nowPlaying = comingPlayer;
        if (nowPlaying == player) {
            tvNowPlaying.setText("Your turn!");
            adapter.updateBoard(player.getvBoard(), true);
        }
        else {
            tvNowPlaying.setText("CPU's turn!");
            adapter.updateBoard(cpu.getvBoard(), false);
        }
    }

    @Override
    public void onTileClick(int x, int y, ImageView ivTile) {
        selectedTile = new Hit(x, y);
        //Set a blue filter to the selected tile
        if (ivSelectedTile != null) {
            removeFilters(ivSelectedTile);
        }
        ivSelectedTile = ivTile;
        ivSelectedTile.setColorFilter(Color.argb(100, 255, 0,0), PorterDuff.Mode.OVERLAY);
        char letter = (char) (x + 65);
        tvNowPlaying.setText("You clicked on " + letter + " " + (y+1));
    }

    public void removeFilters(ImageView ivTile) {
        ivTile.setColorFilter(Color.argb(0, 0, 0,0), PorterDuff.Mode.OVERLAY);
    }
}
