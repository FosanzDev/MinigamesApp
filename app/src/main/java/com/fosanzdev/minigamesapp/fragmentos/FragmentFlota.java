package com.fosanzdev.minigamesapp.fragmentos;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
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

    //Edit this to change the ships that are going to be used (the length of the ships)
    private static final int[] ships = {5, 4, 3, 3, 2};

    //Edit this to change the size of the board
    private static final int BOARD_SIZE_X = 8;
    private static final int BOARD_SIZE_Y = 8;

    //The two players and the one that is playing now
    private Player player;
    private CpuPlayer cpu;
    private Player nowPlaying;

    //The game, the selected tile and the imageview of the selected tile
    private Game game;
    private Hit selectedTile;
    private ImageView ivSelectedTile;

    //The adapter for the recyclerview
    private FlotaAdapter adapter;

    //The views
    private TextView tvNowPlaying;
    private Button bFire;
    private RecyclerView rvBoard;

    //Listeners for the buttons (to change its behaviour during the game)
    View.OnClickListener fireListener;
    View.OnClickListener changePlayerListener;

    //Empty constructor
    public FragmentFlota() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flota, container, false);

        // Setting up players
        player = new HumanPlayer("Esteban");
        cpu = new CpuPlayer("CPU");

        //Setting up player board (Note that the board is built randomly atm)
        Board playerBoard = BoardBuilder.buildRandomBoard(ships, BOARD_SIZE_X, BOARD_SIZE_Y);
        player.setvBoard(VBoardBuilder.parseBoard(playerBoard));

        //Setting up CPU board (Random by default)
        Board cpuBoard = BoardBuilder.buildRandomBoard(ships, BOARD_SIZE_X, BOARD_SIZE_Y);
        cpu.setvBoard(VBoardBuilder.parseBoard(cpuBoard));
        cpu.setVisibleBoard(player.getvBoard().getEnemyPOV());

        //Setting up RecyclerView for player board
        rvBoard = v.findViewById(R.id.rvBoard);
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()) {

            //This will disable the scrolling of the recyclerview (no animation)
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        //Set the adapter for the recyclerview. First time it is the player board, so the player
        // Knows where its ships are
        adapter = new FlotaAdapter(player.getvBoard(), rvBoard, this);
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
        //Doing this onViewCreated to ensure that the views are created

        //Create the game when all the views are created
        game = new Game(this, player, cpu);

        //Create the listeners for the buttons
        fireListener = v -> {
            if (selectedTile == null) {
                tvNowPlaying.setText("You must select a tile first!");
                return;
            }
            game.manageHit(selectedTile, nowPlaying);
        };

        changePlayerListener = v -> {
            if (nowPlaying == player) {
                onTurnChange(cpu);
            } else {
                onTurnChange(player);
            }
        };
    }

    /**
     * This is called when the game is created. It does a little dialog to introduce the game
     */
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
                Log.i("Game", "CPU Dialog interrupted");
            }
        });

        t.start();

        //Do a temporary listener for the start button which interrupts the dialog and starts the game
        bFire.setOnClickListener(v -> {
            t.interrupt();
            game.start();
        });
    }

    /**
     * This is called when the game starts. It sets the listener for the fire button
     */
    @Override
    public void onGameStart() {
        //Player starts first. CHANGE THIS TO CHANGE WHO STARTS FIRST
        onTurnChange(player);

        //Set the listener for the fire button
        bFire.setText("Fire!");
        bFire.setOnClickListener(fireListener);
    }

    /**
     * This is called when the game ends. It disables the fire button and sets the text to Game Over
     * Also show a message depending on who won
     */
    @Override
    public void onGameEnd() {
        bFire.setEnabled(false);
        bFire.setText("Game Over!");
        bFire.setOnClickListener(null);

        tvNowPlaying.setText("Game Over -- " + (nowPlaying == player ? "You won!" : "You lost!"));
    }

    /**
     * This is called when the turn changes. It sets the text to the player that is playing now
     * @param comingPlayer Player that is going to play now
     */
    @Override
    public void onTurnChange(Player comingPlayer) {
        //Set the listener for the fire button
        bFire.setText("Fire!");
        bFire.setOnClickListener(fireListener);

        //Remove the filter from the selected tile and reset the selected tile
        if (ivSelectedTile != null) removeFilters(ivSelectedTile);
        ivSelectedTile = null;
        selectedTile = null;

        //Set the player that is playing now and update the board
        nowPlaying = comingPlayer;
        if (nowPlaying == player) {
            tvNowPlaying.setText("Your turn!");
            adapter.updateBoard(cpu.getvBoard(), false);
        }
        else {
            bFire.setEnabled(false);
            tvNowPlaying.setText("CPU's turn!");
            adapter.updateBoard(player.getvBoard(), true);
            //If it is the CPU turn, start a thread to simulate the CPU thinking
            startCpuTurn();
        }
    }

    /**
     * This is called when a hit is performed. It updates the board and sets the text to the result
     */
    @Override
    public void onHit() {
        adapter.updateBoard(nowPlaying == player ? cpu.getvBoard() : player.getvBoard(), nowPlaying != player);
        bFire.setText("Next");
        bFire.setEnabled(true);
        bFire.setOnClickListener(changePlayerListener);
    }

    /**
     * This is called when a miss is performed. It updates the board and sets the text to the result
     */
    @Override
    public void onShipHit(){
        tvNowPlaying.setText("Ship hit!");
    }

    /**
     * This is called when a miss is performed. It updates the board and sets the text to the result
     */
    @Override
    public void onMiss(){
        tvNowPlaying.setText("Miss!");
    }

    /**
     * This is called when an invalid hit is performed. It sets the text to the result
     */
    @Override
    public void onInvalidHit(){
        tvNowPlaying.setText("You already hit that tile!");
    }

    /**
     * This is called when a ship is sunk. It sets the text to the result
     */
    @Override
    public void onShipSunk() {
        tvNowPlaying.setText("Ship sunk!");
    }

    /**
     * Returns the player that is playing now
     * @return Player that is playing now
     */
    @Override
    public Player getNowPlaying() {
        return nowPlaying;
    }

    /**
     * This is called to simulate the CPU thinking. It starts an external thread that interacts with
     * the UI.
     */
    private void startCpuTurn(){
        Thread t = new Thread(() -> {
            try {
                //Wait a little bit to simulate the CPU thinking
                tvNowPlaying.post(() -> tvNowPlaying.setText("CPU is thinking..."));
                Thread.sleep(1000);
                Hit cpuHit = cpu.play();
                //Perform a click in the rvBoard
                rvBoard.post(() -> {
                    LinearLayout llRow = (LinearLayout) rvBoard.getChildAt(cpuHit.getX());
                    ImageView ivTile = (ImageView) llRow.getChildAt(cpuHit.getY());
                    selectTile(cpuHit.getX(), cpuHit.getY(), ivTile);
                });
                Thread.sleep(1000);
                tvNowPlaying.post(() -> tvNowPlaying.setText("CPU is FIREING!"));
                Thread.sleep(1000);
                //Perform a click in the fire button
                tvNowPlaying.post(() -> bFire.performClick());
            } catch (InterruptedException e) {
                Log.e("Game", "CPU turn interrupted");
            }
        });
        t.start();
    }

    /**
     * Notify the fragment that a tile was clicked. This is used by the adapter to notify the fragment
     * @param x X coordinate of the tile
     * @param y Y coordinate of the tile
     * @param ivTile ImageView of the tile
     */
    @Override
    public void onTileClick(int x, int y, ImageView ivTile) {
        //Ignore the click if it is not the player turn
        if (nowPlaying == cpu || nowPlaying == null)
            return;

        //Select the tile
        //This method is external to correctly discern between the player and the CPU clicks
        selectTile(x, y, ivTile);
    }

    /**
     * Sets up a tile as selected
     * @param x X coordinate of the tile
     * @param y Y coordinate of the tile
     * @param ivTile ImageView of the tile
     */
    public void selectTile(int x, int y, ImageView ivTile) {
        //Change the selected tile
        selectedTile = new Hit(x, y);
        //Remove the filter from the previous selected tile
        if (ivSelectedTile != null) {
            removeFilters(ivSelectedTile);
        }

        //Set a color filter to the selected tile. By default it is an overlay of red
        ivSelectedTile = ivTile;
        ivSelectedTile.setColorFilter(Color.argb(100, 255, 0,0), PorterDuff.Mode.OVERLAY);
        char letter = (char) (x + 65);
        //Set the text to the tile selected
        tvNowPlaying.setText("Tile selected: " + letter + (y+1));
    }

    /**
     * Remove the color filter from a tile
     * @param ivTile ImageView of the tile
     */
    public void removeFilters(ImageView ivTile) {
        ivTile.setColorFilter(Color.argb(0, 0, 0,0), PorterDuff.Mode.OVERLAY);
    }
}
