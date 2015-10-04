package com.robo.colorreact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StartScreenActivity extends AppCompatActivity {

    int score;
    SharedPreferences prefs;
    TextView highScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("key", 0);

        highScoreTextView = (TextView) findViewById(R.id.highScoreTextView);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startApp(View view) {

        Intent startGame = new Intent(this, GameScreenActivity.class);
        final int result = 1;
        startActivityForResult(startGame, result);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView scoreTextView =(TextView) findViewById(R.id.scoreTextView);
        String scoreString = data.getStringExtra("score");

        score = Integer.valueOf(scoreString);
        scoreTextView.setText("Your Score : " + score);

        updateHighScore();
    }

    public void updateHighScore() {

        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("key", 0);

        if (score > highScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("key", score);
            editor.apply();

            highScoreTextView.setText("High Score: " + String.valueOf(score));
        }
    }

}
