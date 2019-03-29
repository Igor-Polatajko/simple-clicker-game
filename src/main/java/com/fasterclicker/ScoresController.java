package com.fasterclicker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static android.content.Context.MODE_PRIVATE;

public class ScoresController {


    private Gson gson;
    private SharedPreferences scoreSharedPreferences;
    private SharedPreferences clicksLevelSharedPreferences;
    private SharedPreferences.Editor editor;
    private Map<Integer, ArrayList<Double>> scores;
    private Integer currentClicksLevel;

    public ScoresController(Context context) {
        scoreSharedPreferences = context.getSharedPreferences("scores", MODE_PRIVATE);
        clicksLevelSharedPreferences = context.getSharedPreferences("gameClicksLevel", MODE_PRIVATE);
        editor = scoreSharedPreferences.edit();
        gson = new Gson();
        currentClicksLevel = clicksLevelSharedPreferences.getInt("gameClicksLevel", 100);
    }

    public ArrayList<Double> getBestScores() {
        initializeScoresArray();
        return scores.get(currentClicksLevel);
    }

    public Double getBestScore() {
        initializeScoresArray();
        return scores.get(currentClicksLevel).get(0);
    }

    public void addScore(double score) {
        initializeScoresArray();
        scores.get(currentClicksLevel).add(score);
        saveScoresArray();
    }


    private void initializeScoresArray() {
        String json = scoreSharedPreferences.getString("scores", null);
        Type type = new TypeToken<Map<Integer, ArrayList<Double>>>() {
        }.getType();
        scores = gson.fromJson(json, type);

        if (scores == null) {
            scores = new TreeMap<>();
        }
        if (scores.get(currentClicksLevel) == null) {
            scores.put(currentClicksLevel, new ArrayList<Double>());
        }

        Collections.sort(scores.get(currentClicksLevel));

    }

    private void saveScoresArray() {
        String json = gson.toJson(scores);
        editor.putString("scores", json);
        editor.apply();
    }
}
