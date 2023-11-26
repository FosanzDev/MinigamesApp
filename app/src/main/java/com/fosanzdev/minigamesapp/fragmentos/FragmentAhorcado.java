package com.fosanzdev.minigamesapp.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fosanzdev.minigamesapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FragmentAhorcado extends Fragment {
    private final ArrayList<String> listaPalabras = new ArrayList<>();

    public FragmentAhorcado(){
        super(R.layout.fragment_ahorgado);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        crearListaDePalabras();
    }

    public void crearListaDePalabras(){
        //listaPalabras = new ArrayList<>();
        InputStream file = getResources().openRawResource(R.raw.words_es);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();
            listaPalabras.add(s);
        }
    }
}