package io.github.GeneShips.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.GeneShips.MathUtils;

public class ShipPhysics {
    private final Ship ship;
    private final Body body;

    // Константы из старой физики (можно вынести в отдельный config)
    private final float BASE_THRUST = 8f;
    private final float SPEED_DEPENDENT_DRAG = 0.04f;

    public ShipPhysics(Ship ship, Body body) {
        this.ship = ship;
        this.body = body;
    }

    public void update(float deltaTime) {
        applyThrust();
        applySteering();
        syncShipWithBody();
    }

    private void applyThrust() {
        // Рассчитываем силу тяги на основе генома
        float thrustForce = calculateThrustForce();

        Vector2 force = new Vector2(thrustForce, 0);
        force = body.getWorldVector(force); // преобразуем в мировые координаты
        body.applyForceToCenter(force, true);
    }

    private float calculateThrustForce() {
        // Берем параметры из генома через ship
        float maxSpeed = ship.getEngine().getMaxSpeed();
        float acceleration = ship.getAcceleration();

        // Текущая скорость из Box2D (в пикселях/сек)
        float currentSpeed = body.getLinearVelocity().len() / 0.01f;

        // Эффективность на высоких скоростях (как в старой физике)
        float speedEfficiency = MathUtils.map(currentSpeed, 0, maxSpeed, 1.0f, 0.2f);

        // Масса корабля из Box2D
        float mass = body.getMass();

        // Фактор массы (как в старой физике)
        float massFactor = 50f / calculateMass();

        float throttle = 1.0f; // можно сделать управляемым

        return BASE_THRUST * acceleration * throttle * speedEfficiency * massFactor;
    }

    private float calculateMass() {
        // Та же логика что в старой физике
        float volume = ship.getLength() * ship.getWidth() * ship.getWidth() * 0.1f;
        return Math.max(10f, volume);
    }

    private void applySteering() {
        // TODO: Добавить повороты когда будет ИИ
        // body.applyTorque(turnForce, true);
    }

    private void syncShipWithBody() {
        // Синхронизируем позицию корабля с Box2D телом
        ship.setX(body.getPosition().x / 0.01f); // METERS_TO_PIXELS
        ship.setY(body.getPosition().y / 0.01f);
        ship.setRotation(body.getAngle());

        // Обновляем скорость в ship (может пригодиться для рендеринга или логики)
        ship.setSpeed(body.getLinearVelocity().len() / 0.01f);
    }
}
