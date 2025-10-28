package io.github.GeneShips.generators;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.GeneShips.entities.Ship;
import io.github.GeneShips.entities.ShipPhysics;


public class ShipGenerator {

    private final ShipBodyFactory shipBodyFactory;

    public ShipGenerator(ShipBodyFactory shipBodyFactory) {
        this.shipBodyFactory = shipBodyFactory;
    }

    private float map(float value, float rangeS, float rangeE, float enRangeS, float enRangeE) {
        return enRangeS + (enRangeE - enRangeS) * ((value - rangeS) / (rangeE - rangeS));
    }

    public Ship createRandomShip(float x, float y) {
        float[] genome = genGenome();
        Ship ship = new Ship(x, y, genome);


        Body body = shipBodyFactory.createShipBody(ship);
        ShipPhysics physics = new ShipPhysics(ship, body);
        ship.setPhysics(physics);

        return ship;
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
