package com.fosanzdev.minigamesapp.battleship.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.minigamesapp.R;
import com.fosanzdev.minigamesapp.battleship.board.VBoard;
import com.fosanzdev.minigamesapp.battleship.board.VTile;

public class FlotaAdapter extends RecyclerView.Adapter<FlotaAdapter.BoardHolder>{

    private final VBoard board;

    public FlotaAdapter(VBoard board){
        this.board = board;
    }

    @NonNull
    @Override
    public BoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.battleship_row, parent, false);
        return new BoardHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardHolder holder, int position) {
        holder.bindRow(board.getTiles()[position]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class BoardHolder extends RecyclerView.ViewHolder{

        private final LinearLayout llRow;

        public BoardHolder(@NonNull View itemView) {
            super(itemView);
            llRow = itemView.findViewById(R.id.llbattleshipRow);
        }

        public void bindRow(VTile[] tiles){
            for (VTile tile : tiles) {
                ImageView ivTile = new ImageView(itemView.getContext());
                ivTile.setImageResource(tile.getResource());
                llRow.addView(ivTile);
            }
        }
    }
}
