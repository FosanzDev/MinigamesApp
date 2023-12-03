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
import android.widget.LinearLayout;
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
import com.fosanzdev.minigamesapp.battleship.game.CpuPlayer;
import com.fosanzdev.minigamesapp.battleship.game.Game;
import com.fosanzdev.minigamesapp.battleship.game.HumanPlayer;
import com.fosanzdev.minigamesapp.battleship.game.Player;

public class FragmentFlota extends Fragment implements Game.GameListener, FlotaAdapter.OnTileClickListener {
    private static int[] ships = {5, 4, 3, 3, 2};
    private static int BOARD_SIZE_X = 10;
    private static int BOARD_SIZE_Y = 10;

    private Player player;
    private CpuPlayer cpu;
    private Player nowPlaying;

    private Game game;
    private Hit selectedTile;
    private ImageView ivSelectedTile;
    private FlotaAdapter adapter;
    private boolean selectionEnabled = true;


    private TextView tvNowPlaying;
    private Button bFire;
    private RecyclerView rvBoard;

    View.OnClickListener fireListener;
    View.OnClickListener changePlayerListener;

    public FragmentFlota() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flota, container, false);

        player = new HumanPlayer("Esteban");
        cpu = new CpuPlayer("CPU");

        //Setting up player board
        Board playerBoard = BoardBuilder.buildRandomBoard(ships, BOARD_SIZE_X, BOARD_SIZE_Y);
        player.setvBoard(VBoardBuilder.parseBoard(playerBoard));

        //Setting up CPU board
        Board cpuBoard = BoardBuilder.buildRandomBoard(ships, BOARD_SIZE_X, BOARD_SIZE_Y);
        cpu.setvBoard(VBoardBuilder.parseBoard(cpuBoard));
        cpu.setVisibleBoard(player.getvBoard().getEnemyPOV());

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

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        game = new Game(this, player, cpu);

        fireListener = v -> {
            if (selectedTile == null) {
                tvNowPlaying.setText("You must select a tile first!");
                return;
            }
            if (nowPlaying == player) {
                game.manageHit(selectedTile, true);
            } else {
                game.manageHit(selectedTile, false);
            }
        };

        changePlayerListener = v -> {
            if (nowPlaying == player) {
                onTurnChange(cpu);
            } else {
                onTurnChange(player);
            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onGameCreated() {
        bFire.setText("Start");
        selectionEnabled = false;

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
        onTurnChange(player);

        bFire.setOnClickListener(fireListener);
    }

    @Override
    public void onGameEnd() {

    }

    @Override
    public void onTurnChange(Player comingPlayer) {
        bFire.setText("Fire!");
        bFire.setOnClickListener(fireListener);

        if (ivSelectedTile != null) removeFilters(ivSelectedTile);
        ivSelectedTile = null;
        selectedTile = null;
        nowPlaying = comingPlayer;
        if (nowPlaying == player) {
            selectionEnabled = true;
            tvNowPlaying.setText("Your turn!");
            adapter.updateBoard(cpu.getvBoard(), false);;
        }
        else {
            selectionEnabled = false;
            bFire.setEnabled(false);
            tvNowPlaying.setText("CPU's turn!");
            adapter.updateBoard(player.getvBoard(), true);
            startCpuTurn();
        }
    }

    @Override
    public void onHit(Hit hit) {
        adapter.updateBoard(nowPlaying == player ? cpu.getvBoard() : player.getvBoard(), true);
        bFire.setText("Next");
        bFire.setEnabled(true);
        bFire.setOnClickListener(changePlayerListener);
    }

    private void startCpuTurn(){
        Thread t = new Thread(() -> {
            try {
                tvNowPlaying.post(() -> tvNowPlaying.setText("CPU is thinking..."));
                Thread.sleep(1000);
                Hit cpuHit = cpu.play();
                //Perform a click in the rvBoard
                rvBoard.post(() -> {
                    LinearLayout llRow = (LinearLayout) rvBoard.getChildAt(cpuHit.getX());
                    ImageView ivTile = (ImageView) llRow.getChildAt(cpuHit.getY());
                    ivTile.performClick();
                });
                Thread.sleep(1000);
                tvNowPlaying.post(() -> tvNowPlaying.setText("CPU is FIREING!"));
                Thread.sleep(1000);
                tvNowPlaying.post(() -> bFire.performClick());
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                return;
            }
        });
        t.start();
    }

    @Override
    public void onTileClick(int x, int y, ImageView ivTile) {
        if (!selectionEnabled)
            if (nowPlaying == player || nowPlaying == null)
                return;
        selectedTile = new Hit(x, y);
        //Set a blue filter to the selected tile
        if (ivSelectedTile != null) {
            removeFilters(ivSelectedTile);
        }
        ivSelectedTile = ivTile;
        ivSelectedTile.setColorFilter(Color.argb(100, 255, 0,0), PorterDuff.Mode.OVERLAY);
        char letter = (char) (x + 65);
        tvNowPlaying.setText("Tile selected: " + letter + (y+1));
    }

    public void removeFilters(ImageView ivTile) {
        ivTile.setColorFilter(Color.argb(0, 0, 0,0), PorterDuff.Mode.OVERLAY);
    }
}
