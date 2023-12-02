package com.fosanzdev.minigamesapp.logicaJuegos;

import java.util.ArrayList;

public class LogicaAhorcado {

    public static boolean characterUnico(String entradaString){
        return entradaString.length() == 1;
    }


    public static boolean confirmarLetra(String letra){
        String consonants="bcdfghjklmnñpqrstvwxyz";
        String vocales="aáàeéèiíìoóòuúù";
        return consonants.contains(letra) || vocales.contains(letra);
    }

    public static boolean confirmalSiHayLetra(String palabra,String letra){
        return palabra.contains(letra);
    }

    public static String eliminarAcentos(String texto){
        String origen="áàéèíìóòúù";
        String destino="aaeeiioouu";
        for(int i = 0; i < origen.length(); i++){
            texto = texto.replace(origen.charAt(i), destino.charAt(i));
        }
        return texto;
    }

    public static String ocultarPalabraSecreta(int longitud){
        StringBuilder palabra = new StringBuilder();
        for(int i = 0; i < longitud;i++){
            palabra.append("_");
        }
        return palabra.toString();
    }

    public static ArrayList<Integer> getPosLetra(String palbaraSecreta, String letra){
        ArrayList<Integer> posLetras = new ArrayList<>();
        for(int i = 0; i < palbaraSecreta.length(); i++){
            String letraPalabra = String.valueOf(palbaraSecreta.charAt(i));
            if(letraPalabra.equalsIgnoreCase(letra)){
                posLetras.add(i);
            }
        }
        return posLetras;
    }

    public static String mostrarLetraCorrecta(String palabaraOcultada,ArrayList<Integer> posLetras, String letrra){
            char[] charArray = palabaraOcultada.toCharArray();
            char letraAChar = letrra.charAt(0);
            int pos;
            for(int i = 0; i < posLetras.size(); i++){
                pos = posLetras.get(i);
                charArray[pos] = letraAChar;
            }
            return new String(charArray);
    }

    public static boolean siLetraEnLista(ArrayList<String> arrayDeLetrasUsadas,String letra){
        return arrayDeLetrasUsadas.contains(letra);
    }

}
