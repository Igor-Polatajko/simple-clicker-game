package com.fasterclicker;

import android.graphics.Color;

public class ColorsGenerator {

    public int getScreenColorTLM(int startClicksNumber, int currentClickNumber) {
        double perOneClick = (double) 255 / startClicksNumber;
        int r, g;
        double coef = 3;
        if (currentClickNumber > 2 * (startClicksNumber / coef)) {
            r = 255;
            g = (int) (255 - (coef * perOneClick * (currentClickNumber - (2 * startClicksNumber / coef))));

            if (g > 215) {
                g = 215;
            }
        } else if (currentClickNumber < (startClicksNumber / coef)) {
            r = (int) Math.ceil((coef * perOneClick * currentClickNumber));
            g = 215;
        } else {
            r = 255;
            g = 215;
        }
        return Color.argb(255, r, g, 30);
    }

    public int getScreenColor(int startClicksNumber, int currentClickNumber) {
        double perOneClick = (double) 255 / startClicksNumber;
        int r = (int) Math.ceil(perOneClick * currentClickNumber);
        int g = 255 - r;
        return Color.argb(255, r, g, 30);
    }
}
