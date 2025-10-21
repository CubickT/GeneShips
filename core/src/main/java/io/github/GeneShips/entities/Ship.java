package io.github.GeneShips.entities;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Ship {

    public static final int GEN_Length = 0;

//    Индексы генов
    public static final int GEN_Width = 1;
    public static final int GEN_BowAngle = 2;
    public static final int GEN_Count = 3;
    private static final float MIN_LENGTH = 50;

//    МАКС МИН ВЕЛИЧИНЫ ДЛЯ НАСТРОЙКИ
    private static final float MAX_LENGTH = 200;
    private static final float MIN_WIDTH = 20;
    private static final float MAX_WIDTH = 50;
    private static final float MIN_BOW_ANGLE = 0f;
    private static final float MAX_BOW_ANGLE = 1f;
    float[] genome;
    float x, y;
    float speed;
    float rotation;
    float angularVel;

    public Ship(float x, float y, float[] genome) {
        this.x = x;
        this.y = y;
        this.rotation = 0;

        this.genome = genome;
    }

    private float map(float value, float rangeS, float rangeE, float enRangeS, float enRangeE) {
        return enRangeS + (enRangeE - enRangeS) * ((value - rangeS) / (rangeE - rangeS));
    }

    public void shipUpdate(float time) {

        x += (float) (speed * cos(rotation) * time);
        y += (float) (speed * sin(rotation) * time);

        rotation += angularVel * time;

    }


//    ГЕТТЕРЫ ГЕНОВ

    public float getLength() {
        return map(genome[GEN_Length], 0, 1, MIN_LENGTH, MAX_LENGTH);
    }

    public float getWidth() {
        return map(genome[GEN_Width], 0, 1, MIN_WIDTH, MAX_WIDTH);
    }

    public float getBowAngle() {
        return map(genome[GEN_BowAngle], 0F, 1F, MIN_BOW_ANGLE, MAX_BOW_ANGLE);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
