package com.fosanzdev.minigamesapp.battleship.adapters;

import android.content.Context;
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

public class FlotaAdapter extends RecyclerView.Adapter<FlotaAdapter.BoardHolder>{

    private final VBoard board;
    private final Context context;

    private RecyclerView rvBoard;

    public FlotaAdapter(VBoard board, Context context, RecyclerView rvBoard){
        this.board = board;
        this.context = context;
        this.rvBoard = rvBoard;
    }

    @NonNull
    @Override
    public BoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.battleship_row, parent, false);
        return new BoardHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardHolder holder, int position) {
        rvBoard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rvBoard.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = rvBoard.getWidth();
                int height = rvBoard.getHeight();
                System.out.println("Width: " + width);
                System.out.println("Height: " + height);
                System.out.println("Using: " + (Math.min(width, height)));
                int numColumns = board.getTiles().length;
                int imageSize = Math.min(width, height) / numColumns;
                holder.bindRow(board.getTiles()[position], imageSize);
            }
        });
    }

    @Override
    public int getItemCount() {
        return board.getTiles().length;
    }

    static class BoardHolder extends RecyclerView.ViewHolder{

        private final Context context;

        private final LinearLayout llRow;


        public BoardHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            llRow = itemView.findViewById(R.id.llbattleshipRow);
        }

        public void bindRow(VTile[] tiles, int width){
            llRow.removeAllViews();
            for (int i =0; i<tiles.length; i++) {
                ImageView ivTile = new ImageView(itemView.getContext());
                ivTile.setImageResource(tiles[i].getResource());
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, width);
                ivTile.setLayoutParams(params);
                ivTile.setPadding(1, 1, 1, 1);
                ivTile.setBackgroundColor(context.getResources().getColor(R.color.black));
                ivTile.setScaleType(ImageView.ScaleType.FIT_XY);
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
                llRow.addView(ivTile);
            }
        }
    }
}
