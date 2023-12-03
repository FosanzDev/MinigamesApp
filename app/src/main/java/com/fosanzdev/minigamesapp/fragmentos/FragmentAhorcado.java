package com.fosanzdev.minigamesapp.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private boolean jugar;
    private boolean victoria;
    Random rand;

    /**
     * Inflación del fragmento de ahorcado
     */
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
        final int[] numIntentos = {6};
        final String[] textNumeIntentos = {String.valueOf(numIntentos[0])};
        String letrasOriginales ="";
        ImageView ivAhorcadoEstadoImage = view.findViewById(R.id.imageView);
        //Inicio del juego donde cogemos la palabra de la lsita
        //Y convertimos/ocultamos la palabra
        final String[] palabraAleatoria = {LogicaAhorcado.eliminarAcentos(getPalabraDeLista())};
        final String[][] palabaraCodigo = {{LogicaAhorcado.ocultarPalabraSecreta(palabraAleatoria[0].length())}};
        tvIntentos.setText(textNumeIntentos[0]);
        tvLetras.setText(letrasOriginales);
        tvPalbaraSecreta.setText(palabaraCodigo[0][0]);

        EditText etLetra = view.findViewById(R.id.etLetra);
        Button button = view.findViewById(R.id.btJugar);
        StringBuilder sbLetra = new StringBuilder();
        final String[] letra = new String[1];
        ArrayList<String> letrasUsadas = new ArrayList<>();
        final ArrayList<Integer>[] postionDeLetras = new ArrayList[]{new ArrayList<>()};
        jugar = true;
        victoria = false;
        button.setOnClickListener(new View.OnClickListener() {
            /**
             * El listener que usmaos para coger la letra
             * @param v El view que era clicked
             */
            @Override
            public void onClick(View v) {
                sbLetra.append(etLetra.getText());
                letra[0] = sbLetra.toString();
                letra[0] = letra[0].toLowerCase();
                letra[0] = LogicaAhorcado.eliminarAcentos(letra[0]);
                if(numIntentos[0] == 0 && !victoria) {
                    Toast.makeText(getActivity(), "No tienes mas Intentos", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Ha perdido", Toast.LENGTH_SHORT).show();

                }else if(!jugar){
                    Toast.makeText(getActivity(), "Has ganado", Toast.LENGTH_SHORT).show();
                }else{
                    if(LogicaAhorcado.characterUnico(letra[0])) {
                        if(LogicaAhorcado.siLetraEnLista(letrasUsadas,letra[0])){
                            Toast.makeText(getActivity(), "Este letra ya esta usada", Toast.LENGTH_SHORT).show();
                        }else{
                            letrasUsadas.add(letra[0]);
                            tvLetras.setText(letrasUsadas.toString());
                            if(LogicaAhorcado.confirmarLetra(letra[0])){
                                if(LogicaAhorcado.confirmalSiHayLetra(palabraAleatoria[0], letra[0])){
                                    postionDeLetras[0] = LogicaAhorcado.getPosLetra(palabraAleatoria[0], letra[0]);
                                    palabaraCodigo[0][0] = LogicaAhorcado.mostrarLetraCorrecta(palabaraCodigo[0][0], postionDeLetras[0],letra[0]);
                                    tvPalbaraSecreta.setText(palabaraCodigo[0][0]);
                                    if(palabaraCodigo[0][0].equals(palabraAleatoria[0])){
                                        Toast.makeText(getActivity(), "HAS GANADO", Toast.LENGTH_SHORT).show();
                                        jugar = false;
                                    }
                                }else{
                                    Toast.makeText(getActivity(), "Este letra no existe en la palabra", Toast.LENGTH_SHORT).show();
                                    numIntentos[0] -= 1;
                                    textNumeIntentos[0] = String.valueOf(numIntentos[0]);
                                    tvIntentos.setText(textNumeIntentos[0]);
                                    if(numIntentos[0] == 0 ) {
                                        Toast.makeText(getActivity(), "No tienes mas Intentos", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(), "GAME OVER", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(), "Para jugar otra vez pula el boton de reinicio", Toast.LENGTH_SHORT).show();
                                    }
                                    if(numIntentos[0] == 5)
                                        ivAhorcadoEstadoImage.setImageResource(R.drawable.hangman_5);
                                    if(numIntentos[0] == 4)
                                        ivAhorcadoEstadoImage.setImageResource(R.drawable.hangman_4);
                                    if(numIntentos[0] == 3)
                                        ivAhorcadoEstadoImage.setImageResource(R.drawable.hangman_3);
                                    if(numIntentos[0] == 2)
                                        ivAhorcadoEstadoImage.setImageResource(R.drawable.hangman_2);
                                    if(numIntentos[0] == 1)
                                        ivAhorcadoEstadoImage.setImageResource(R.drawable.hangman_1);
                                    if(numIntentos[0] == 0)
                                        ivAhorcadoEstadoImage.setImageResource(R.drawable.hangman_0);
                                }
                            }else
                                Toast.makeText(getActivity(), "El caracter intorducido no es una letra", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(getActivity(), "Tenies que introducir UNA letra", Toast.LENGTH_SHORT).show();
                }
                sbLetra.setLength(0);
                postionDeLetras[0].clear();
            }
        });

        Button btRestart = view.findViewById(R.id.bt_reinicio);
        btRestart.setOnClickListener(new View.OnClickListener() {
            /**
             * El litstener que usamos para resetar el jeugo
             * @param v El view que era clicked
             */
            @Override
            public void onClick(View v) {
                palabraAleatoria[0] = LogicaAhorcado.eliminarAcentos(getPalabraDeLista());
                palabaraCodigo[0] = new String[]{LogicaAhorcado.ocultarPalabraSecreta(palabraAleatoria[0].length())};
                letrasUsadas.clear();
                ivAhorcadoEstadoImage.setImageResource(R.drawable.hangman_6);
                jugar = true;
                victoria = false;
                numIntentos[0] = 6;
                textNumeIntentos[0] = String.valueOf(numIntentos[0]);
                tvIntentos.setText(textNumeIntentos[0]);
                tvLetras.setText(letrasUsadas.toString());
                tvPalbaraSecreta.setText(palabaraCodigo[0][0]);
            }
        });
    }

    /**
     * Un metodo void que llena el arraylist de palabras
     */
    public void crearListaDePalabras(){
        InputStream file = getResources().openRawResource(R.raw.words_es);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();
            listaPalabras.add(s);
        }
    }

    /**
     * El metodo coje una palabara aleatoria de la lista y devulvela, usando un random para
     * eligir una posicion aleotorea para despues usar el get para cojer una palabra
     * del arraylist
     * @return un String, la palabra secreta
     */
    public String getPalabraDeLista(){
        rand = new Random();
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