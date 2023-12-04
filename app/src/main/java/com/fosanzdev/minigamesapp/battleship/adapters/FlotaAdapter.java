package com.fosanzdev.minigamesapp.battleship.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.minigamesapp.R;
import com.fosanzdev.minigamesapp.battleship.board.VBoard;
import com.fosanzdev.minigamesapp.battleship.board.VTile;

/**
 * Adapter for the board
 */
public class FlotaAdapter extends RecyclerView.Adapter<FlotaAdapter.BoardHolder>{

    /**
     * Interface for the tile click listener
     * This will inform the user of this adapter when a tile is clicked
     */
    public interface OnTileClickListener{
        void onTileClick(int row, int column, ImageView ivTile);
    }

    //VBorad to be displayed. This includes the Board, the enemy POV and the friendly POV
    private VBoard board;
    private final RecyclerView rvBoard;
    private final OnTileClickListener listener;

    //This will determine if it is the enemy POV or the friendly POV
    private boolean visible = true;

    public FlotaAdapter(VBoard board, RecyclerView rvBoard, OnTileClickListener listener){
        this.board = board;
        this.rvBoard = rvBoard;
        this.listener = listener;
    }

    /**
     * Update the board with the new VBoard
     * @param board new VBoard
     * @param visible if it is the enemy POV or the friendly POV (or simply if it is visible or not)
     */
    public void updateBoard(VBoard board, boolean visible){
        this.board = board;
        this.visible = visible;
        //A whole dataset is changed due to some errors while updating a single tile (row dissapearing)
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.battleship_row, parent, false);
        return new BoardHolder(v, listener);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull BoardHolder holder, int position) {

        //This will wait until the view is created to get the width and height of the RecyclerView
        //This is needed to calculate the size of the images (tiles) to be displayed
        //This may seem contradictory since it can lead to the confusion that the view is already created, but it is not
        rvBoard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Necessary to remove the listener after the view is created
                rvBoard.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //Get the width and height of the RecyclerView
                int width = rvBoard.getWidth();
                int height = rvBoard.getHeight();
                int numColumns = board.getTiles().length;

                //Calculate the size of the images (tiles) to be displayed
                // In order to adapt to screen ratio, the size will be the minimum between width and height
                // so it never exceeds the screen size
                int imageSize = Math.min(width, height) / numColumns;

                //Load the enemy POV or the friendly POV depending on the visible variable
                if (!visible){
                    holder.bindRow(board.getEnemyPOV()[position], imageSize);
                }
                else
                    holder.bindRow(board.getTiles()[position], imageSize);
            }
        });
    }

    @Override
    public int getItemCount() {
        return board.getTiles().length;
    }

    static class BoardHolder extends RecyclerView.ViewHolder{
        private final LinearLayout llRow;
        private final OnTileClickListener listener;

        /**
         * Constructor
         * @param itemView view
         * @param listener listener for the tile click
         */
        public BoardHolder(@NonNull View itemView, OnTileClickListener listener) {
            super(itemView);
            this.listener = listener;
            //Get the LinearLayout that will contain a row of tiles
            llRow = itemView.findViewById(R.id.llbattleshipRow);
        }

        public void bindRow(VTile[] tiles, int width){
            //Ensure the row is empty
            llRow.removeAllViews();

            //For each tile in the row, create an ImageView and add it to the LinearLayout
            for (int i =0; i<tiles.length; i++) {
                ImageView ivTile = new ImageView(itemView.getContext());
                //Set the image resource of the tile (ship, water, hit, etc)
                ivTile.setImageResource(tiles[i].getResource());
                //Set a padding of 1 pixel to separate the tiles
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, width);
                ivTile.setLayoutParams(params);
                ivTile.setPadding(1, 1, 1, 1);
                //Set the background color to black. Since it has padding, the black will be visible
                // as a separation between the tiles
                ivTile.setBackgroundColor(Color.BLACK);
                //Set the scale type to fit the image to the ImageView
                ivTile.setScaleType(ImageView.ScaleType.FIT_XY);

                //Set the rotation of the tile depending on its orientation
                // Several tests were done in order to choose between loading a different image for each orientation
                // or doing an image rotation. The rotation was chosen since it is more efficient and less memory consuming
                switch (tiles[i].getOrientation()){
                    case N:
                        ivTile.setRotation(0);
                        break;
                    case S:
                        ivTile.setRotation(180);
                        break;
                    case E:
                        ivTile.setRotation(90);
                        break;
                    case W:
                        ivTile.setRotation(270);
                        break;
                }

                //Set the listener for the tile click
                int finalI = i;
                ivTile.setOnClickListener(v -> {
                    listener.onTileClick(getAdapterPosition(), finalI, ivTile);
                });

                //Add the ImageView to the LinearLayout
                llRow.addView(ivTile);
            }
        }
    }
}
