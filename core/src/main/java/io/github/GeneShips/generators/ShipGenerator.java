package io.github.GeneShips.generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class ShipGenerator {
    float x, y;
    float length, width;
    float bowAngle;

    public void generateShipAt(float x, float y) {
        float length = MathUtils.random(70, 200);
        float width = MathUtils.random(40, 100);
        float bowAngle = MathUtils.random(0, 1);

        Gdx.app.log("SHIP_PARAMS", "Длина: " + length + ", Ширина: " + width);

    }
}
