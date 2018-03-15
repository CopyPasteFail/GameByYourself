package com.game.hacka.game.games.memory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.game.hacka.game.R;

import java.util.ArrayList;

/**
 * Created by hezidimri on 15/03/2018.
 */

public class MemoryGame extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("https://vignette.wikia.nocookie.net/marveldatabase/images/1/1e/Ben_Reilly_%28Earth-616%29_from_Sensational_Spider-Man_Vol_1_0_0001.png/revision/latest?cb=20130722053111"));
        cards.add(new Card("https://vignette.wikia.nocookie.net/marveldatabase/images/1/1e/Ben_Reilly_%28Earth-616%29_from_Sensational_Spider-Man_Vol_1_0_0001.png/revision/latest?cb=20130722053111"));
        cards.add(new Card("https://vignette.wikia.nocookie.net/marveldatabase/images/1/1e/Ben_Reilly_%28Earth-616%29_from_Sensational_Spider-Man_Vol_1_0_0001.png/revision/latest?cb=20130722053111"));
        cards.add(new Card("https://vignette.wikia.nocookie.net/marveldatabase/images/1/1e/Ben_Reilly_%28Earth-616%29_from_Sensational_Spider-Man_Vol_1_0_0001.png/revision/latest?cb=20130722053111"));
        cards.add(new Card("https://vignette.wikia.nocookie.net/marveldatabase/images/1/1e/Ben_Reilly_%28Earth-616%29_from_Sensational_Spider-Man_Vol_1_0_0001.png/revision/latest?cb=20130722053111"));


        RecyclerView recyclerView = findViewById(R.id.memory_game_rv);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, cards);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }
}
