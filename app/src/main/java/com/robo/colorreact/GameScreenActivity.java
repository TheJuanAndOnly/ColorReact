package com.robo.colorreact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Robo on 02/10/2015.
 */
public class GameScreenActivity extends Activity{

    int rndTxt, rndClr,progress = 100, diff = 1;
    double diffPart = 1;
    boolean clrTruth;
    TextView changeBoard;
    ProgressBar changeProgress;
    Button redBtn, greenBtn, blueBtn;
    Button redTxtBtn, greenTxtBtn, blueTxtBtn;
    MyCountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        changeBoard = (TextView) findViewById(R.id.board);
        changeProgress = (ProgressBar) findViewById(R.id.progress);

        redBtn = (Button) findViewById(R.id.redBtnClr);
        greenBtn = (Button) findViewById(R.id.greenBtnClr);
        blueBtn = (Button) findViewById(R.id.blueBtnClr);
        redTxtBtn = (Button) findViewById(R.id.redBtnTxt);
        greenTxtBtn = (Button) findViewById(R.id.greenBtnTxt);
        blueTxtBtn = (Button) findViewById(R.id.blueBtnTxt);

        newBoard();
        startProgressBar();
    }

    public void newBoard() {

        rndTxt = (int) (3 * Math.random()) + 1;
        rndClr = (int) (3 * Math.random()) + 1;

        switch (rndTxt) {
            case 1:
                changeBoard.setText("Red");
                break;
            case 2:
                changeBoard.setText("Green");
                break;
            case 3:
                changeBoard.setText("Blue");
                break;
        }
        switch (rndClr) {
            case 1:
                changeBoard.setTextColor(Color.rgb(255, 0, 0));
                break;
            case 2:
                changeBoard.setTextColor(Color.rgb(0, 128, 0));
                break;
            case 3:
                changeBoard.setTextColor(Color.rgb(0, 0, 255));
                break;
        }
    }

    // B u t t o n s

    public void redTxtBtn(View view) {
        clrTruth = rndTxt == 1;

        redBtn.setClickable(true); greenBtn.setClickable(true); blueBtn.setClickable(true);
        redTxtBtn.setClickable(false); greenTxtBtn.setClickable(false); blueTxtBtn.setClickable(false);
    }

    public void greenTxtBtn(View view) {
        clrTruth = rndTxt == 2;

        redBtn.setClickable(true); greenBtn.setClickable(true); blueBtn.setClickable(true);
        redTxtBtn.setClickable(false); greenTxtBtn.setClickable(false); blueTxtBtn.setClickable(false);
    }

    public void blueTxtBtn(View view) {
        clrTruth = rndTxt == 3;

        redBtn.setClickable(true); greenBtn.setClickable(true); blueBtn.setClickable(true);
        redTxtBtn.setClickable(false); greenTxtBtn.setClickable(false); blueTxtBtn.setClickable(false);
    }
    ////////////////////////////
    Intent goBack = new Intent();
    int score = 0;

    public void redBtn(View view) {

        redBtn.setClickable(false); greenBtn.setClickable(false); blueBtn.setClickable(false);
        redTxtBtn.setClickable(true); greenTxtBtn.setClickable(true); blueTxtBtn.setClickable(true);

        if (clrTruth && rndClr == 1) {
            score++;
            newBoard();

            progress += 20;
            myCountDownTimer.cancel();

            setProgress();
            startProgressBar();
            addDifficulty();
        }
        else {
            progress -= 50;
            myCountDownTimer.cancel();

            setProgress();
            startProgressBar();
        }
    }

    public void greenBtn(View view) {

        redBtn.setClickable(false); greenBtn.setClickable(false); blueBtn.setClickable(false);
        redTxtBtn.setClickable(true); greenTxtBtn.setClickable(true); blueTxtBtn.setClickable(true);

        if (clrTruth && rndClr == 2) {
            score++;
            newBoard();

            progress += 20;
            myCountDownTimer.cancel();

            setProgress();
            startProgressBar();
            addDifficulty();
        }
        else {
            progress -= 50;
            myCountDownTimer.cancel();

            setProgress();
            startProgressBar();
        }
    }

    public void blueBtn(View view) {

        redBtn.setClickable(false); greenBtn.setClickable(false); blueBtn.setClickable(false);
        redTxtBtn.setClickable(true); greenTxtBtn.setClickable(true); blueTxtBtn.setClickable(true);

        if (clrTruth && rndClr == 3) {
            score++;
            newBoard();

            progress += 20;
            myCountDownTimer.cancel();

            setProgress();
            startProgressBar();
            addDifficulty();
        }
        else {
            progress -= 50;
            myCountDownTimer.cancel();

            setProgress();
            startProgressBar();
        }
    }

    // End Buttons

    public void startProgressBar() {
        changeProgress.setProgress(progress);
        myCountDownTimer =  new MyCountDownTimer(progress* 100, 100);
        myCountDownTimer.start();
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            progress = (progress - diff);

            setProgress();
        }

        @Override
        public void onFinish() {
            changeProgress.setProgress(0);
            closeActivity();
        }

    }

    public void addDifficulty() {
        diffPart += 0.25;
        diff = (int) diffPart;
    }

    public void setProgress() {
        if (progress > 100) progress = 100;
        changeProgress.setProgress(progress);

        if (progress <= 0) closeActivity();
    }

    public void closeActivity() {
        String scoreSend = String.valueOf(score);
        goBack.putExtra("score", scoreSend);
        setResult(RESULT_OK, goBack);
        finish();
    }

}
