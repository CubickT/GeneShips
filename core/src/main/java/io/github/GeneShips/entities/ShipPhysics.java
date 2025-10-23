package io.github.GeneShips.entities;

import io.github.GeneShips.Interfaces.ShipPhysProp;
import io.github.GeneShips.Interfaces.ShipState;
import io.github.GeneShips.MathUtils;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ShipPhysics {

    private final ShipPhysProp shipProps;
    private final ShipState shipState;
    private final float BASE_DRAG = 0.12f;
    private final float BASE_THRUST = 8f;
    private final float SPEED_DEPENDENT_DRAG = 0.04f;
    private float dragForce;
    private float thrust;

    public ShipPhysics(ShipPhysProp shipProps, ShipState shipState) {
        this.shipProps = shipProps;
        this.shipState = shipState;
    }

    public void update(float deltaTime) {
        calcDrag();
        calcThrust();

        float speed = shipProps.getSpeed();
        float x = shipProps.getX();
        float y = shipProps.getY();
        float rotation = shipProps.getRotation();
        float angularVel = shipProps.getAngularVel();
        float acceleration = (thrust - dragForce) * deltaTime;

        speed += acceleration;
        float maxSpeed = shipProps.getEngine().getMaxSpeed();
        if (speed > maxSpeed) {
            speed = maxSpeed * 0.95f;
        }


        x += (float) (speed * cos(rotation) * deltaTime);
        y += (float) (speed * sin(rotation) * deltaTime);
        rotation += angularVel * deltaTime;

        shipState.setSpeed(speed);
        shipState.setX(x);
        shipState.setY(y);
        shipState.setRotation(rotation);
        shipState.setAngularVel(angularVel);

    }

//    ФИЗИКА

    public void calcDrag() {
        float bowEfficiency = MathUtils.map(shipProps.getBowAngle(), 0F, 1F, 0.1F, 0.9F);
        float widthPenalty = MathUtils.map(shipProps.getWidth(), 20F, 50F, 0.5F, 1.8F);

        float optimalLength = 120f; // Самая эффективная длина
        float lengthFromOptimal = Math.abs(shipProps.getLength() - optimalLength);
        float lengthEfficiency = 1.0f - (lengthFromOptimal / optimalLength) * 0.3f;

        float geometricDrag = (widthPenalty / bowEfficiency);
        float speedDrag = 1 + SPEED_DEPENDENT_DRAG * shipProps.getSpeed();

        this.dragForce = BASE_DRAG * geometricDrag * speedDrag;

    }

    public void calcThrust() {
//        для отладки макс значение
        float maxSpeed = shipProps.getEngine().getMaxSpeed();
        float speedEfficiency = MathUtils.map(shipProps.getSpeed(), 0, maxSpeed, 1.0f, 0.2f);

        float mass = calculateMass();
        float massFactor = 50f / mass;

        float throttle = 100F;

        this.thrust = BASE_THRUST * shipProps.getAcceleration() * throttle * speedEfficiency * massFactor;
    }

    private float calculateMass() {
        float volume = shipProps.getLength() * shipProps.getWidth() * shipProps.getWidth() * 0.1f;
        return Math.max(10f, volume);
    }


}
