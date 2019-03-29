package com.fasterclicker;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameResultsActivity extends AppCompatActivity {

    private ConstraintLayout clicksNumberLayout;
    private LinearLayout timeTextLayout;
    private Button startGameButton;
    private Button menuButton;
    private TextView clicksNumberText;
    private TextView currentTimeText;
    private TextView bestTimeText;

    private ScoresController scoresController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);


        startGameButton = findViewById(R.id.startGameButton);
        menuButton = findViewById(R.id.menuButton);
        clicksNumberText = findViewById(R.id.clicksNumberText);
        currentTimeText = findViewById(R.id.currentTimeText);
        bestTimeText = findViewById(R.id.bestTimeText);
        clicksNumberLayout = findViewById(R.id.clicksNumberLayout);
        timeTextLayout = findViewById(R.id.timeTextLayout);

        setMargins();

        scoresController = new ScoresController(this);

        Intent intent = getIntent();

        clicksNumberText.setText(intent.getStringExtra("clicksNumber"));

        String currentTimeTextString = "Your time: " + (intent.getStringExtra("time"));
        currentTimeText.setText(currentTimeTextString);

        String bestTimeTextString = "Best time: " + scoresController.getBestScore().toString();
        bestTimeText.setText(bestTimeTextString);
    }

    public void onMenuButtonClick(View view) {
        this.finish();
        Intent menu = new Intent(this, MenuActivity.class);
        startActivity(menu);
    }

    public void onStartGameButtonClick(View view) {
        this.finish();
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }


    private void setMargins() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int y = size.y;


        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) clicksNumberLayout.getLayoutParams();

        lp.setMargins(0, 0, 0, y / 8);

        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) timeTextLayout.getLayoutParams();
        llp.setMargins(0, 0, 0, y / 8);

    }
}
