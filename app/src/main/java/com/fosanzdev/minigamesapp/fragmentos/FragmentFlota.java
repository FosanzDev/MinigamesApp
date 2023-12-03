package com.fosanzdev.minigamesapp.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentFlota extends Fragment {

    VBoard cpuBoard;
    VBoard playerBoard;
    private static int[] ships = {5, 4, 3, 3, 2};
    private static int BOARD_SIZE_X = 10;
    private static int BOARD_SIZE_Y = 10;

    public FragmentFlota() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flota, container, false);
        Board board = BoardBuilder.buildRandomBoard(ships, BOARD_SIZE_X, BOARD_SIZE_Y);
        VBoard vBoard = VBoardBuilder.parseBoard(board);
        RecyclerView rvBoard = v.findViewById(R.id.rvBoard);
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvBoard.setAdapter(new FlotaAdapter(vBoard, getContext(), rvBoard));
        rvBoard.setHasFixedSize(true);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
