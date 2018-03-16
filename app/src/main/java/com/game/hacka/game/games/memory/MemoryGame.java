package com.game.hacka.game.games.memory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.game.hacka.game.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hezidimri on 15/03/2018.
 */

public class MemoryGame extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    int points = 0;

    public interface gameStatusListener {

        void onMatchFound();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);



        final ArrayList<Card> cards = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            cards.add(new Card("https://vignette.wikia.nocookie.net/marveldatabase/images/1/1e/Ben_Reilly_%28Earth-616%29_from_Sensational_Spider-Man_Vol_1_0_0001.png/revision/latest?cb=20130722053111"));
            cards.add(new Card("https://pmcvariety.files.wordpress.com/2017/02/spidermanavengersdowney.jpg?w=1000&h=563&crop=1"));
            cards.add(new Card("https://i.annihil.us/u/prod/marvel/i/mg/6/60/538cd3628a05e.jpg"));
            cards.add(new Card("https://img.ecartelera.com/noticias/44200/44272-q1.jpg"));
            cards.add(new Card("http://images.goodsmile.info/cgm/images/product/20170606/6486/45777/large/0f78fbdf148fd7d0da70b85ad091dd4d.jpg"));
            cards.add(new Card("https://vignette.wikia.nocookie.net/marvelvscapcom/images/e/eb/PeterParker.png/revision/latest?cb=20170818120510"));
            cards.add(new Card("https://i.ytimg.com/vi/3R2uvJqWeVg/maxresdefault.jpg"));
            cards.add(new Card("http://cdn.shopify.com/s/files/1/0170/5178/products/Spidey03.2_1024x1024.png?v=1513486916"));

        }

        Collections.shuffle(cards);


        recyclerView = findViewById(R.id.memory_game_rv);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, cards, new gameStatusListener() {
            @Override
            public void onMatchFound() {
                points = points + 10;

                if (points / 10 == cards.size() / 2) {
                    gameFinished();
                }
            }
        });
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    private void gameFinished() {
        Toast.makeText(MemoryGame.this, "finsih", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemClick(final View view, final int position) {
        Log.i("TAG", "You clicked image " + adapter.getItemImageUrl(position) + ", which is at cell position " + position);


    }
}
