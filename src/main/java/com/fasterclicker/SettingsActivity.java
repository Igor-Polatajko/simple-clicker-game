package com.fasterclicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Map<Integer, Integer> clicksLevels;

    private TextView clicksNumberText;
    private SeekBar clicksNumberSeekBar;

    private Switch TLM_Switch;

    private SharedPreferences currentClicksLevelSharedPreferences;
    private SharedPreferences.Editor currentClicksLevelEditor;
    private SharedPreferences TLM_SP;
    private SharedPreferences.Editor TLM_Editor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeClicksLevels();
        initializeSP();


        clicksNumberText = findViewById(R.id.clicksNumberText);
        clicksNumberSeekBar = findViewById(R.id.clicksNumberSeekBar);
        clicksNumberSeekBar.setMax(90);

        TLM_Switch = findViewById(R.id.TLM_Switch);

        showCurrentSettingsLevel();

        clicksNumberSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int progressLevel = (progress % 10 >= 5) ? progress / 10 + 1 : progress / 10;

                int clicksNumber = getClicksNumber(progressLevel);

                String clicksNumberTextString = "Clicks number: " + clicksNumber;
                clicksNumberSeekBar.setProgress(progressLevel * 10);
                clicksNumberText.setText(clicksNumberTextString);

                currentClicksLevelEditor.putInt("gameClicksLevel",  clicksNumber);
                currentClicksLevelEditor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        TLM_Switch.setOnCheckedChangeListener(this);
    }


    private void showCurrentSettingsLevel() {
        int clicksNumber = currentClicksLevelSharedPreferences.getInt("gameClicksLevel", 100);
        String clicksNumberTextString = "Clicks number: " + clicksNumber;
        clicksNumberSeekBar.setProgress(getProgressLevel(clicksNumber) * 10);
        clicksNumberText.setText(clicksNumberTextString);

        boolean isTLM_Checked = TLM_SP.getBoolean("TLM", false);
        setTLM_SwitchText(isTLM_Checked);
        TLM_Switch.setChecked(isTLM_Checked);
    }

    private int getClicksNumber(int progressLevel) {
        return clicksLevels.get(progressLevel);
    }

    private int getProgressLevel(int clicksNumber) {
        for (Integer key : clicksLevels.keySet()) {
            if (clicksNumber == clicksLevels.get(key)) {
                return key;
            }
        }
        return 1;
    }

    private void initializeClicksLevels() {
        clicksLevels = new TreeMap<>();
        clicksLevels.put(0, 50);
        clicksLevels.put(1, 100);
        clicksLevels.put(2, 200);
        clicksLevels.put(3, 300);
        clicksLevels.put(4, 500);
        clicksLevels.put(5, 1000);
        clicksLevels.put(6, 1500);
        clicksLevels.put(7, 3000);
        clicksLevels.put(8, 5000);
        clicksLevels.put(9, 10000);
    }

    private void initializeSP() {
        currentClicksLevelSharedPreferences = this.getSharedPreferences("gameClicksLevel", MODE_PRIVATE);
        currentClicksLevelEditor = currentClicksLevelSharedPreferences.edit();
        TLM_SP = this.getSharedPreferences("TLM", MODE_PRIVATE);
        TLM_Editor = TLM_SP.edit();
    }

    public void onMenuButtonClick(View view) {
        this.finish();
        Intent menu = new Intent(this, MenuActivity.class);
        startActivity(menu);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setTLM_SwitchText(isChecked);
        TLM_Editor.putBoolean("TLM", isChecked);
        TLM_Editor.apply();
    }

    private void setTLM_SwitchText(boolean isChecked) {
        if (isChecked) {
            TLM_Switch.setText("Traffic light mode: on   ");
        } else {
            TLM_Switch.setText("Traffic light mode: off   ");
        }
    }
}
