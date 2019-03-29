package com.fasterclicker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class BestScoresActivity extends AppCompatActivity {

    private ScoresController scoresController;
    private LinearLayout bestScoresField;
    private int scoresCount = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_scores);

        scoresController = new ScoresController(this);
        bestScoresField = findViewById(R.id.bestScoresField);
        showScores();
    }

    public void onMenuButtonClick(View view) {
        this.finish();
        Intent menu = new Intent(this, MenuActivity.class);
        startActivity(menu);
    }

    private void showScores() {
        ArrayList<Double> scores = scoresController.getBestScores();
        for (int i = 0; i < scoresCount; i++) {
            TextView tw = new TextView(this);
            tw.setTextColor(Color.BLACK);
            tw.setTextSize(30);
            tw.setText("" + (i + 1) + ". ");
            if (scores != null && scores.size() - 1 >= i && scores.get(i) != null) {
                tw.append("" + scores.get(i));
            } else {
                tw.append("----------");
            }
            bestScoresField.addView(tw);
        }
    }
}
