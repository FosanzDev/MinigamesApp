package com.fosanzdev.minigamesapp.logicaJuegos;
import java.util.ArrayList;
public class LogicaAhorcado {
    /**
     * El metodo que confirme si solo inroducimos una letra/caracter
     * @param entradaString es la palabra que introducimos
     * @return devuelve verdadero o falso dependiendo de si
     * la solo hay una letra o no
     */
    public static boolean characterUnico(String entradaString){
        return entradaString.length() == 1;
    }

    /**
     *En este metodo comprobamos si que hemos introducido es una letra
     * o no
     * @param letra que es el caracter unico
     * @return verdadero o falso dependiendo si contiene un consonante o vocal
     * si hay verdadero si no falso
     */
    public static boolean confirmarLetra(String letra){
        String consonants="bcdfghjklmnñpqrstvwxyz";
        String vocales="aáàeéèiíìoóòuúù";
        return consonants.contains(letra) || vocales.contains(letra);
    }

    /**
     * Metodo para confirmar si este letra existe en la pabara aleatoria
     * @param palabra la palabra secreta/aleatoria
     * @param letra el caracter que introduce el jugador
     * @return vedadero o falso, verdadero si existe y falso si no
     */
    public static boolean confirmalSiHayLetra(String palabra,String letra){
        return palabra.contains(letra);
    }

    /**
     * El metodo elimina los accetos de la palabra secreta
     * @param palabraSecreta que recibimos
     * @return la palabra sin accentos
     */
    public static String eliminarAcentos(String palabraSecreta){
        String origen="áàéèíìóòúù";
        String destino="aaeeiioouu";
        for(int i = 0; i < origen.length(); i++){
            palabraSecreta = palabraSecreta.replace(origen.charAt(i), destino.charAt(i));
        }
        return palabraSecreta;
    }

    /**
     * Metodo para 'transformar' la palabra secreta a occultada
     * @param longitudPalabraSecreta para usar en el bucle
     * @return un string de _
     */
    public static String ocultarPalabraSecreta(int longitudPalabraSecreta){
        StringBuilder palabra = new StringBuilder();
        for(int i = 0; i < longitudPalabraSecreta;i++){
            palabra.append("_");
        }
        return palabra.toString();
    }

    /**
     * El metodo para recibir la posicion de las letras un y convertirlos
     * a un array que usamos despues en el ahorcado. Usamos un bucle for
     * para pasar por todas las letras y si coinciden guardamos en el array.
     * En el bucle creamos un variable string
     * @param palbaraSecreta que recibimos de fragmento para la comprobacion de posiciones
     * @param letra que usamos para comprobacion
     * @return un array de numeros
     */
    public static ArrayList<Integer> getPosLetra(String palbaraSecreta, String letra){
        String letraPalabra;
        ArrayList<Integer> posLetras = new ArrayList<>();
        for(int i = 0; i < palbaraSecreta.length(); i++){
             letraPalabra = String.valueOf(palbaraSecreta.charAt(i));
            if(letraPalabra.equalsIgnoreCase(letra)){
                posLetras.add(i);
            }
        }
        return posLetras;
    }

    /**
     * Metodo que usamos para 'mostar' las letra que exiten en el array.
     *  Usamos un bucle para pasar por todas posicines que recibimos del arraylist
     *  Transformamos la palabraOcultada en un charArray apra despues camibar la letra de manera
     *  efficiente
     * @param palabaraOcultada recibimos el varible que vamos a hacer un update
     * @param posLetras que es un array de posiciones cuales hay que camiar
     * @param letrra que existe en la palabra
     * @return String con las letra en la posicion correcta
     */
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

    /**
     * Metodo que usamos para confirma si la letra esta usado o no
     * @param arrayDeLetrasUsadas lista de letras usados
     * @param letra que es un String con 1 caracter
     * @return verdero si la letra existe y falso si no esta en la lista
     */
    public static boolean siLetraEnLista(ArrayList<String> arrayDeLetrasUsadas,String letra){
        return arrayDeLetrasUsadas.contains(letra);
    }
}