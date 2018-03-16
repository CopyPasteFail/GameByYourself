package com.game.hacka.game.games.memory;

/**
 * Created by hezidimri on 15/03/2018.
 */

public class Card {

    String cardImageUrl ;
    public boolean openedCard = false;
    public boolean cardFound = false;


    public Card(String cardImageUrl) {
        this.cardImageUrl = cardImageUrl;
    }
}
