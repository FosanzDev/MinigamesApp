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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        manager = getSupportFragmentManager();
        int itemID = item.getItemId();
        if(itemID == R.id.action_hangman){
            fragmentToCome = new FragmentAhorcado();
            cambiarFragment(fragmentToCome);
            return  true;
        }else if(itemID == R.id.action_tictac){

        }
        return false;
    }
    private void cambiarFragment(Fragment fragment){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flMainView, fragment);
        fragmentTransaction.commit();
    }
}