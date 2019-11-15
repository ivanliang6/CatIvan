package com.example.ivancat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private FavoritesFragment favoritesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction =manager .beginTransaction();
        if(searchFragment == null){
            searchFragment = new SearchFragment();

        }
        if(favoritesFragment == null){
            favoritesFragment = new FavoritesFragment();

        }
        transaction.add(R.id.fl, favoritesFragment).add(R.id.fl, searchFragment).hide(favoritesFragment);
        transaction.show(searchFragment);
        transaction.commit();

    }



    public void fav(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(searchFragment);
        transaction.hide(favoritesFragment);
        transaction.show(favoritesFragment).commit();
    }

    public void home(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(searchFragment);
        transaction.hide(favoritesFragment);
        transaction.show(searchFragment).commit();
    }
}
