package com.game.hacka.game;

/**
 * Created by omerb on 15/3/2018.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SimonSaysActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "SimonSaysActivity::";

    int mLevel = 0;
    List<Integer> mComputerChoice = new ArrayList<>();
    List<Integer> mUserPressed = new ArrayList<>();

    Handler mTimerHandler;
    TimerRunnable mTimerRunnable;

    enum Result
    {
        GAME_OVER,
        CONTINUE,
        WIN
    }

    final static int[] COLORS_BY_ID_MAP = {
        R.id.imgVw_simon_says_button_blue,
        R.id.imgVw_simon_says_button_red,
        R.id.imgVw_simon_says_button_green,
        R.id.imgVw_simon_says_button_yellow};

    TextView mResultView;
    ImageView mQuarterBlue;
    ImageView mQuarterGreen;
    ImageView mQuarterRed;
    ImageView mQuarterYellow;
    ImageView mPlayButton;

    public class TimerRunnable implements Runnable
    {
        int mIntervalMilli;
        int mTimesRepeated = 0;
        Handler mTimerHandler;

        TimerRunnable(int pIntervalMilli, Handler pTimerHandler)
        {
            mIntervalMilli = pIntervalMilli;
            mTimerHandler = pTimerHandler;
        }

        @Override
        public void run()
        {
//            prepareFor();
            mResultView.setText("LISTEN");
            playSound(mComputerChoice.get(mTimesRepeated));
            if (++mTimesRepeated < mLevel)
            {
                mTimerHandler.postDelayed(this, mIntervalMilli);
            }
            else
            {
                mResultView.setText("REPEAT THE PATTERN");
                //reset for next sequence
                mTimesRepeated = 0;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simons_says);

        mResultView = findViewById(R.id.txtVw_simon_says_result);
        mQuarterBlue = findViewById(R.id.imgVw_simon_says_button_blue);
        mQuarterBlue.setOnClickListener(this);
        mQuarterGreen = findViewById(R.id.imgVw_simon_says_button_green);
        mQuarterGreen.setOnClickListener(this);
        mQuarterRed = findViewById(R.id.imgVw_simon_says_button_red);
        mQuarterRed.setOnClickListener(this);
        mQuarterYellow = findViewById(R.id.imgVw_simon_says_button_yellow);
        mQuarterYellow.setOnClickListener(this);
        mPlayButton = findViewById(R.id.imgVw_simon_says_button_play);
        mPlayButton.setOnClickListener(this);
        mResultView.setText("LET'S START");

        //runs without a timer by re-posting this handler at the end of the runnable
        mTimerHandler = new Handler();
        // set timer params
        mTimerRunnable = new TimerRunnable(2000, mTimerHandler);

    }

    private void increaseLevel()
    {
        Random lRandom = new Random();
        int lRandomInt = lRandom.nextInt(4);
        mComputerChoice.add(COLORS_BY_ID_MAP[lRandomInt]);
        mLevel++;
        Log.d(TAG, "increaseLevel: level is now = " + mLevel + ", lRandomInt = " + lRandomInt
        + ", chose = "+ COLORS_BY_ID_MAP[lRandomInt]);
    }

    @Override
    public void onClick(View pView)
    {
        Log.d(TAG, "onClick: pView.getId() = " + pView.getId());
        if (pView.getId() == R.id.imgVw_simon_says_button_play)
        {
            //reset everything
            mLevel = 0;
            mComputerChoice.clear();
            mUserPressed.clear();
            increaseLevel();
            mTimerHandler.postDelayed(mTimerRunnable, 0);
            return;
        }
        playSound(pView.getId());
        Result lResult = checkResult(pView.getId());
        if (lResult == Result.GAME_OVER)
        {
            mResultView.setText("GAME OVER");
        }
        else if (lResult == Result.WIN)
        {
            mResultView.setText("GOOD JOB, GET READY!");
            increaseLevel();
            mUserPressed.clear();
            mTimerRunnable = new TimerRunnable(500, mTimerHandler);
        }
    }

    private Result checkResult(int pColorId)
    {
        mUserPressed.add(pColorId);
        int lLastIndex = mUserPressed.size()-1;
        Log.d(TAG, "checkResult: lLastIndex = " + lLastIndex + ", pColor = " + pColorId);
        //noinspection NumberEquality
        if (mUserPressed.get(lLastIndex) != mComputerChoice.get(lLastIndex))
        {
            return Result.GAME_OVER;
        }
        // if the last index equals to the level number - the sequence has been repeated successfully by the player
        else if (lLastIndex+1 == mLevel)
        {
            return Result.WIN;
        }
        else
        {
            return Result.CONTINUE;
        }
    }

    private void playSound(int pColorId)
    {
        int lResourceId = R.raw.blue;
        Log.e(TAG, "playSound: pColorId = " + pColorId);
        switch (pColorId)
        {
            case R.id.imgVw_simon_says_button_yellow:
            {
                lResourceId = R.raw.yellow;
                break;
            }

            case R.id.imgVw_simon_says_button_red:
            {
                lResourceId = R.raw.red;
                break;
            }
            case R.id.imgVw_simon_says_button_green:
            {
                lResourceId = R.raw.green;
                break;
            }
            case R.id.imgVw_simon_says_button_blue:
            {
                lResourceId = R.raw.blue;
                break;
            }
        }

        MediaPlayer mp = MediaPlayer.create(this, lResourceId);
        try
        {
            if (mp.isPlaying())
            {
                mp.stop();
                mp.release();
                mp = MediaPlayer.create(this, lResourceId);
            }
            mp.start();
        }
        catch(Exception pE)
        {
            Log.e(TAG, "playing sound failed: ", pE);
        }
    }

    private void prepareFor()
    {
        if (mResultView.getText().equals("READY"))
        {
            mResultView.setText("SET");
        }
        else if (mResultView.getText().equals("SET"))
        {
            mResultView.setText("GO");
        }
        else if (mResultView.getText().equals("GO"))
        {
            mResultView.setText("READY");
        }
    }
}
