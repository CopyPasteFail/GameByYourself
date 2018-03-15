package com.game.hacka.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.game.hacka.game.enteties.User;
import com.game.hacka.game.firebase.DataProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hezidimri on 15/03/2018.
 */

public class TestClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataProvider.getInstance().createUser(new User("T",11));
    }
}
