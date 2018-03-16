package com.game.hacka.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.game.hacka.game.games.memory.MemoryGame;

/**
 * Created by hezidimri on 16/03/2018.
 */

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


    }

    public void startMemory(View view) {
        startActivity(new Intent(this, MemoryGame.class));
    }


    public void startSimon(View view) {
    }


}
