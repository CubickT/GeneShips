package io.github.GeneShips.generators;

import com.badlogic.gdx.math.MathUtils;
import io.github.GeneShips.entities.Ship;


public class ShipGenerator {

    private float map(float value, float rangeS, float rangeE, float enRangeS, float enRangeE) {
        return enRangeS + (enRangeE - enRangeS) * ((value - rangeS) / (rangeE - rangeS));
    }

    public Ship createRandomShip(float x, float y) {
        float[] genome = genGenome();
        return new Ship(x, y, genome);
    }

    private float[] genGenome() {
        float[] genome = new float[Ship.GEN_Count];
        genome[Ship.GEN_Length] = MathUtils.random(0F, 1F);
        genome[Ship.GEN_Width] = MathUtils.random(0F, 1F);
        genome[Ship.GEN_BowAngle] = MathUtils.random(0F, 1F);
        genome[Ship.GEN_Engine] = MathUtils.random(0F, 1F);
        return genome;
    }


}
