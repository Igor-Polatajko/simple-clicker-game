package com.fasterclicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button startGameButton;
    private Button showRecordsButton;
    private Button settingsButton;
    private Button exitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startGameButton = findViewById(R.id.startGameButton);
        showRecordsButton = findViewById(R.id.showRecordsButton);
        settingsButton = findViewById(R.id.settingsButton);
        exitButton = findViewById(R.id.exitButton);
    }

    public void onStartGameButtonClick(View view) {
        this.finish();
        Intent startGame = new Intent(this, MainActivity.class);
        startActivity(startGame);
    }

    public void onShowRecordsButtonClick(View view) {
        this.finish();
        Intent showRecords = new Intent(this, BestScoresActivity.class);
        startActivity(showRecords);
    }

    public void onSettingsButtonClick(View view) {
        this.finish();
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    public void onExitButtonClick(View view) {
        finishAffinity();
        System.exit(0);
    }
}
