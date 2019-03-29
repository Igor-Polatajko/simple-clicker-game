package com.fasterclicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences TLM_SP;

    private ConstraintLayout mainLayout;
    private ProgressBar clicksProgressBar;
    private Button menuButton;
    private TextView clicksNumberText;
    private TextView timerText;
    private int startClicksNumber;
    private int currentClicksNumber;

    private ColorsGenerator colorsGenerator = new ColorsGenerator();
    private ScoresController scoresController;

    private Handler handler;
    private Timer timer;
    private TimerTask stopWatch;
    private int secondsPassed;
    private long startTime;
    private long stopTime;

    private boolean isGameStarted = false;
    private boolean isGameFinished = false;

    private boolean isTLM_Active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TLM_SP = this.getSharedPreferences("TLM", MODE_PRIVATE);
        isTLM_Active = TLM_SP.getBoolean("TLM", false);


        sharedPreferences = this.getSharedPreferences("gameClicksLevel", MODE_PRIVATE);

        startClicksNumber = sharedPreferences.getInt("gameClicksLevel", 100);

        scoresController = new ScoresController(this);

        mainLayout = findViewById(R.id.mainLayout);
        clicksProgressBar = findViewById(R.id.clicksProgressBar);
        menuButton = findViewById(R.id.menuButton);
        clicksNumberText = findViewById(R.id.clicksNumberText);
        timerText = findViewById(R.id.timerText);


        currentClicksNumber = startClicksNumber;

        synchronizeView();

        Toast clickToast = Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_SHORT);
        clickToast.setGravity(Gravity.CENTER, 0, 0);
        clickToast.show();
    }

    public void onMenuButtonClick(View view) {
        Intent menu = new Intent(this, MenuActivity.class);
        startActivity(menu);
        this.finish();
    }

    public void onScreenClick(View view) {
        if (!isGameFinished) {
            if (!isGameStarted) {
                startTime = System.nanoTime();
                startTimer();
                isGameStarted = true;
            }


            currentClicksNumber--;
            synchronizeView();


            if (currentClicksNumber == 0) {
                stopTime = System.nanoTime();
                timer.cancel();
                isGameFinished = true;


                showResultsWindow();
            }
        }
    }

    private void startTimer() {
        handler = new Handler();
        timer = new Timer();
        stopWatch = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        secondsPassed++;
                        String timerTextString = "" +
                                ((secondsPassed / 60) < 10 ? "0" : "") +
                                ((int) (secondsPassed / 60)) +
                                ":" +
                                ((secondsPassed % 60) < 10 ? "0" : "") +
                                (secondsPassed % 60);
                        timerText.setText(timerTextString);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(stopWatch, 1000, 1000);
    }

    private void synchronizeView() {
        clicksNumberText.setText("" + currentClicksNumber);
        int currentColor;
        if (isTLM_Active) {
            currentColor = colorsGenerator.getScreenColorTLM(startClicksNumber, currentClicksNumber);
        } else {
            currentColor = colorsGenerator.getScreenColor(startClicksNumber, currentClicksNumber);
        }
        mainLayout.setBackgroundColor(currentColor);
        clicksProgressBar.setProgress((int) (100 - (((double) 100 / startClicksNumber) * currentClicksNumber)));
    }


    private void showResultsWindow() {
        this.finish();

        Intent resultsWindow = new Intent(this, GameResultsActivity.class);

        double gameTime = Double.parseDouble(getGameTime());

        scoresController.addScore(gameTime);

        resultsWindow.putExtra("time", ("" + gameTime));
        resultsWindow.putExtra("clicksNumber", ("" + startClicksNumber));
        startActivity(resultsWindow);
    }

    private String getGameTime() {
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        return df.format(((double) (stopTime - startTime)) / Math.pow(10, 9));
    }
}
