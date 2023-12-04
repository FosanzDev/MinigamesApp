package com.fosanzdev.minigamesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fosanzdev.minigamesapp.fragmentos.FragmentAhorcado;
import com.fosanzdev.minigamesapp.fragmentos.FragmentFlota;
import com.fosanzdev.minigamesapp.fragmentos.FragmentStart;
import com.fosanzdev.minigamesapp.fragmentos.FragmentTicTacToe;


public class MainActivity extends AppCompatActivity {
    FragmentManager manager;
    FragmentTransaction fragmentTransaction;
    Fragment fragmentToCome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flMainView, new FragmentStart());
        fragmentTransaction.commit();
    }
    /**
     * metodo que crea el menu
     * @param menu The options menu in which you place your items.
     *
     * @return verdadero para crear el menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    /**
     *
     * @param item La opción del menu que era elgida permite
     *             el cambio del fragmento par
     *
     * @return verdadero en caso que eligimos un item que es un icono del menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        manager = getSupportFragmentManager();
        int itemID = item.getItemId();
        if(itemID == R.id.action_hangman){
            fragmentToCome = new FragmentAhorcado();
            cambiarFragment(fragmentToCome);
            return  true;
        }else if(itemID == R.id.action_tictac){
            fragmentToCome = new FragmentTicTacToe();
            cambiarFragment(fragmentToCome);
            return  true;
        }else if(itemID == R.id.action_battleship){
            fragmentToCome = new FragmentFlota();
            cambiarFragment(fragmentToCome);
            return true;
        }
        return false;
    }
    /**
     * El metodo permite el cambio de los fragmentos con el fragmentTransaction que
     * es un variable global para que el metedo puede accderlo  cuando llamamos
     * en vez de crear de nuevo cada vez que cambiamos fragmentos
     * @param fragment que Usamos la Clase del Fragmento en onOptionItemSelecte
     *                 para fragmentTransaction para intercambiar entre los fragmentos
     */

    private void cambiarFragment(Fragment fragment){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flMainView, fragment);
        fragmentTransaction.commit();
    }
}