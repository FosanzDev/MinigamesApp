package com.fosanzdev.minigamesapp.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.minigamesapp.R;

import com.fosanzdev.minigamesapp.battleship.board.VBoard;

public class FragmentFlota extends Fragment {

    VBoard cpuBoard;
    VBoard playerBoard;

    public FragmentFlota() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flota, container, false);
        RecyclerView rvBoard = v.findViewById(R.id.rvBoard);
        RowAdapter rowAdapter = new RowAdapter(cpuBoard);
        rvBoard.setAdapter(rowAdapter);
        rvBoard.setHasFixedSize(true);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
