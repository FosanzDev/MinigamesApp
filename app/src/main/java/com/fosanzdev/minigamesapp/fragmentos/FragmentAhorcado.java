package com.fosanzdev.minigamesapp.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fosanzdev.minigamesapp.R;
import com.fosanzdev.minigamesapp.logicaJuegos.LogicaAhorcado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
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
        TextView tvPalbaraSecreta= view.findViewById(R.id.tvPalabraSecreta);
        TextView tvIntentos = view.findViewById(R.id.tvIntentos);
        TextView tvLetras = view.findViewById(R.id.tvLetras);
        String intentosOrgininal = "6";
        String letrasOriginales ="";
        //Inicio del juego donde cogemos la palabra de la lsita
        //Y convertimos/ocultamos la palabra
        String palabraAleatoria = getPalabraDeLista();
        String palabaraCodigo =ocultarPalabraSecreta(palabraAleatoria.length());
        tvIntentos.setText(intentosOrgininal);
        tvLetras.setText(letrasOriginales);
        tvPalbaraSecreta.setText(palabaraCodigo);

        EditText etLetra = view.findViewById(R.id.etLetra);
        Button button = view.findViewById(R.id.btJugar);
        StringBuilder sbLetra = new StringBuilder();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbLetra.append(etLetra.getText());
                if(LogicaAhorcado.LetraUnica(sbLetra.toString()))
                    Toast.makeText(getActivity(), "The length is correct", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "It is too long", Toast.LENGTH_SHORT).show();

                //if()
                //else
                sbLetra.setLength(0);
            }
        });
    }

    public void crearListaDePalabras(){
        InputStream file = getResources().openRawResource(R.raw.words_es);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();
            listaPalabras.add(s);
        }
    }
    public String getPalabraDeLista(){
        Random rand = new Random();
        int min = 0;
        int max = listaPalabras.size();

        int posAleatoria = rand.nextInt(max - min +1)+min;
        return listaPalabras.get(posAleatoria);
    }

    public String ocultarPalabraSecreta(int longitud){
        StringBuilder palabra = new StringBuilder();
        for(int i = 0; i < longitud;i++){
            palabra.append("_");
        }
        return palabra.toString();
    }

}