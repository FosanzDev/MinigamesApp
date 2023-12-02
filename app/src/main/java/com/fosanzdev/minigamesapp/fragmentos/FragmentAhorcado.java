package com.fosanzdev.minigamesapp.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fosanzdev.minigamesapp.R;
import com.fosanzdev.minigamesapp.logicaJuegos.LogicaAhorcado;

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
        String palabraAleatoria = LogicaAhorcado.eliminarAcentos(getPalabraDeLista());
        final String[] palabaraCodigo = {LogicaAhorcado.ocultarPalabraSecreta(palabraAleatoria.length())};
        tvIntentos.setText(intentosOrgininal);
        tvLetras.setText(letrasOriginales);
        tvPalbaraSecreta.setText(palabaraCodigo[0]);

        EditText etLetra = view.findViewById(R.id.etLetra);
        Button button = view.findViewById(R.id.btJugar);
        StringBuilder sbLetra = new StringBuilder();
        final String[] letra = new String[1];
        ArrayList<String> letrasUsadas = new ArrayList<>();
        final ArrayList<Integer>[] postionDeLetras = new ArrayList[]{new ArrayList<>()};
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sbLetra.append(etLetra.getText());
                letra[0] = sbLetra.toString();
                letra[0] = letra[0].toLowerCase();
                letra[0] = LogicaAhorcado.eliminarAcentos(letra[0]);

                if(LogicaAhorcado.characterUnico(letra[0])) {
                    //
                    if(LogicaAhorcado.siLetraEnLista(letrasUsadas,letra[0])){
                        Toast.makeText(getActivity(), "Este letra ya esta usada", Toast.LENGTH_SHORT).show();
                    }else{
                        letrasUsadas.add(letra[0]);
                        tvLetras.setText(letrasUsadas.toString());
                        if(LogicaAhorcado.confirmarLetra(letra[0])){
                            if(LogicaAhorcado.confirmalSiHayLetra(palabraAleatoria, letra[0])){
                                postionDeLetras[0] = LogicaAhorcado.getPosLetra(palabraAleatoria, letra[0]);
                                //DELETE
                                Toast.makeText(getActivity(), palabraAleatoria, Toast.LENGTH_SHORT).show();
                                //
                                palabaraCodigo[0] = LogicaAhorcado.mostrarLetraCorrecta(palabaraCodigo[0], postionDeLetras[0],letra[0]);
                                tvPalbaraSecreta.setText(palabaraCodigo[0]);
                            }else{
                                Toast.makeText(getActivity(), "Este letra no existe", Toast.LENGTH_SHORT).show();
                                //TODO cambiar el imagen cuando la letra es incorecta
                            }
                        }else
                            Toast.makeText(getActivity(), "El caharcter intorducido no es una letra", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getActivity(), "Tenies que introducir UNA letra", Toast.LENGTH_SHORT).show();

                //if()
                //else
                sbLetra.setLength(0);
                postionDeLetras[0].clear();
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



}
/*

 */

/*

 */