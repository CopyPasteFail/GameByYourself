package com.game.hacka.game.firebase;

import com.game.hacka.game.enteties.User;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hezidimri on 15/03/2018.
 */

public class DataProvider {

    private static DataProvider instance;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DataProvider() {
        database = FirebaseDatabase.getInstance();
    }


    public static DataProvider getInstance() {
        if (instance == null)
            instance = new DataProvider();

        return instance;
    }

    private String USERS_REF = "users";

    public void createUser(User user) {
        database.getReference(USERS_REF).setValue(user);
    }
}
