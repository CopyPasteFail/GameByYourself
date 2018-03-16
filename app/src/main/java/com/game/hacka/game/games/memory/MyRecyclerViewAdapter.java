package com.game.hacka.game.games.memory;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.hacka.game.R;

import java.util.ArrayList;

/**
 * Created by hezidimri on 15/03/2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Card> mCards;
    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    static boolean clickEnabled = true;

    int lastCardPos;
    String lastOpenedCard;
    View lastOpenedCardView;

    MemoryGame.gameStatusListener gameStatusListener;
    String backDeckUrl = "https://img00.deviantart.net/ff45/i/2017/142/2/2/fantasy_card_back__1_by_gameliberty-dazglsi.png";

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<Card> cards, MemoryGame.gameStatusListener gameStatusListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mCards = cards;
        mContext = context;
        this.gameStatusListener = gameStatusListener;


        for (Card card : mCards) {
            Glide.with(context)
                    .load(card.cardImageUrl);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("imageClicked", "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
        View view = mInflater.inflate(R.layout.item_memory_card, parent, false);

        final ViewHolder holder = new ViewHolder(view);

        if ((holder.getAdapterPosition() > -1 && !mCards.get(holder.getAdapterPosition()).cardFound) || (holder.getAdapterPosition() < 0))
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageClicked(holder.image, holder.getAdapterPosition());
                }
            });

        return holder;
    }

    private void imageClicked(final ImageView image, final int position) {
        Log.i("imageClicked", "image " + image + " position " + position);
        if (mCards.get(position).cardFound) {
            return;
        }

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(image, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(image, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Card card = mCards.get(position);

                if (card.openedCard) {
                    Glide.with(mContext)
                            .load(backDeckUrl)
                            .into(image);
                    card.openedCard = false;
                } else {
                    Glide.with(mContext)
                            .load(card.cardImageUrl)
                            .into(image);
                    card.openedCard = true;
                }

                if (lastOpenedCard == null) {
                    // first card drawn
                    Log.e("card", "first");

                    lastOpenedCard = card.cardImageUrl; //getItemImageUrl(position);
                    lastOpenedCardView = image;
                    lastCardPos = position;
                    //image.setOnClickListener(null);
                } else if (lastOpenedCard.equals(card.cardImageUrl)) {
                    // second card drawn and we gut match
                    Log.e("card", "second match");

                    gameStatusListener.onMatchFound();
                    //lastOpenedCardView.setOnClickListener(null);
                    //image.setOnClickListener(null);
                    updateElementsFound(lastOpenedCard);
                    lastOpenedCard = null;
                    lastOpenedCardView = null;
                } else {
                    // second card drawn but not
                    Log.e("card", "second not match");
                    mCards.get(lastCardPos).openedCard = false;
                    mCards.get(position).openedCard = false;

                    lastOpenedCard = null;


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    }, 500);

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                            flipViewWithImage((ImageView) image, backDeckUrl);
//                            flipViewWithImage((ImageView) lastOpenedCardView, (ImageView) image,  backDeckUrl);
//
//                        }
//                    }, 1000);


                }


                oa2.start();

                MyRecyclerViewAdapter.clickEnabled = true;
            }
        });
        oa1.start();
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Card card = mCards.get(position);

        if (!card.cardFound) {
            Log.e("card", "not found in position : " + position);

            Glide.with(mContext)
                    .load(backDeckUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.image);

            Glide.with(mContext)
                    .load(backDeckUrl)
                    .into(holder.image);

        } else {
            Log.e("card", "found in position : " + position);
            Glide.with(mContext)
                    .load(card.cardImageUrl)
                    .into(holder.image);
        }


    }

    private void flipViewWithImage(final ImageView iv, ImageView image, final String imageUrl) {


        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(iv, "scaleX", 0f, 1f);
        final ObjectAnimator oa3 = ObjectAnimator.ofFloat(image, "scaleX", 1f, 0f);
        final ObjectAnimator oa4 = ObjectAnimator.ofFloat(image, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa3.setInterpolator(new DecelerateInterpolator());
        oa4.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);


//                Glide.with(mContext)
//                        .load(imageUrl)
//                        .into(iv);
//                notifyItemChanged(lastCardPos);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                }, 500);

            }
        });
        oa1.start();

        oa3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);


//                Glide.with(mContext)
//                        .load(imageUrl)
//                        .into(iv);
//                notifyItemChanged(lastCardPos);
                // notifyDataSetChanged();

            }
        });
        oa3.start();


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public void updateElementsFound(String lastOpenedCard) {
        for (Card card : mCards) {
            if (card.cardImageUrl.equals(lastOpenedCard))
                card.cardFound = true;
        }

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;


        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_card_iv_front);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // convenience method for getting data at click position
    String getItemImageUrl(int position) {
        return mCards.get(position).cardImageUrl;
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
