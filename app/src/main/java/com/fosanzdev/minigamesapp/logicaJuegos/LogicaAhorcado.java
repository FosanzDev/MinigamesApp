package com.fosanzdev.minigamesapp.logicaJuegos;

public class LogicaAhorcado {

    public static boolean LetraUnica(String entradaString){
        if(entradaString.length() != 1){
            return  false;
        }
        return true;
    }
}
