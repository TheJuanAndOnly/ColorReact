package com.robo.colorreact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StartScreenActivity extends AppCompatActivity {

    int score, gameModeToStart = 1;
    SharedPreferences prefs;
    TextView highScoreTextView, scoreTextView, gameModeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        prefs = this.getSharedPreferences("highScoreSave", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);

        highScoreTextView = (TextView) findViewById(R.id.highScoreTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        gameModeTextView = (TextView) findViewById(R.id.gameModeTextView);

        highScoreTextView.setText("High Score: " + String.valueOf(highScore));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sounds) {
            boolean b = item.isChecked();
            item.setChecked(!b);
        }
        if (id == R.id.action_vibration) {
            boolean b = item.isChecked();
            item.setChecked(!b);
        }
        if (id == R.id.action_resetProgress) {
            deleteProgressDialog();
        }

        return super.onOptionsItemSelected(item);
    }
    public void deleteProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StartScreenActivity.this);

        builder.setTitle("Delete Progress");

        builder.setMessage("Are you sure you want to delete all your progress?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg0) {

                        SharedPreferences prefs = getSharedPreferences("highScoreSave", Context.MODE_PRIVATE);
                        prefs.edit().clear().apply();
                        score = 0;
                        highScoreTextView.setText("High Score : 0");
                        scoreTextView.setText(" ");

                        Toast.makeText(StartScreenActivity.this, "Progress Reset", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg0) {
                    }
                });

        AlertDialog alertdialog = builder.create();

        alertdialog.show();
    }

    public void gameModeLeft(View view) {
        gameModeToStart--;
        if (gameModeToStart > 2) gameModeToStart = 1;
        else if (gameModeToStart < 1) gameModeToStart = 2;

        switch (gameModeToStart) {
            case 1:
                gameModeTextView.setText(R.string.game_mode_1);
                break;
            case 2:
                gameModeTextView.setText(R.string.game_mode_2);
                break;
        }
    }

    public void gameModeRight(View view) {
        gameModeToStart ++;
        if (gameModeToStart > 2) gameModeToStart = 1;
        else if (gameModeToStart < 1) gameModeToStart = 2;

        switch (gameModeToStart) {
            case 1:
                gameModeTextView.setText(R.string.game_mode_1);
                break;
            case 2:
                gameModeTextView.setText(R.string.game_mode_2);
                break;
        }
    }

    public void startApp(View view) {

        switch (gameModeToStart) {
            case 1:
                Intent start1 = new Intent(this, GameScreenActivity.class);
                final int result1 = 1;
                startActivityForResult(start1, result1);
                break;
            case 2:
                Intent start2 = new Intent(this, ThreesomeActivity.class);
                final int result2 = 1;
                startActivityForResult(start2, result2);
                break;
        }

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String scoreString = data.getStringExtra("score");

        score = Integer.valueOf(scoreString);
        scoreTextView.setText("Your Score : " + score);

        updateHighScore();
    }

    public void updateHighScore() {

        prefs = this.getSharedPreferences("highScoreSave", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);

        if (score > highScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highScore", score);
            editor.apply();

            highScoreTextView.setText("High Score: " + String.valueOf(score));
            highScore = score;

            highScoreTextView.setText("High Score : " + String.valueOf(highScore));
        }
    }


}
