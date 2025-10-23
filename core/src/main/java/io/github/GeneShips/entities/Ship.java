package io.github.GeneShips.entities;

import com.badlogic.gdx.Gdx;
import io.github.GeneShips.Interfaces.ShipPhysProp;
import io.github.GeneShips.Interfaces.ShipState;
import io.github.GeneShips.MathUtils;

public class Ship implements ShipPhysProp, ShipState {


    //    Индексы генов

    public static final int GEN_Length = 0;
    public static final int GEN_Width = 1;
    public static final int GEN_BowAngle = 2;
    public static final int GEN_Engine = 3;
    public static final int GEN_Count = 4;

//    МАКС МИН ВЕЛИЧИНЫ ДЛЯ НАСТРОЙКИ

    private static final float MIN_LENGTH = 50;
    private static final float MAX_LENGTH = 200;
    private static final float MIN_WIDTH = 20;
    private static final float MAX_WIDTH = 50;
    private static final float MIN_BOW_ANGLE = 0f;
    private static final float MAX_BOW_ANGLE = 1f;

    private final float[] genome;
    private float x, y;
    private float speed;
    private float rotation;
    private float angularVel;
    private float speedPenalty;
    private float thrust;

    private Engine engine;
    private ShipPhysics shipPhysics;


    public Ship(float x, float y, float[] genome) {
        this.x = x;
        this.y = y;
        this.rotation = 0;
        this.speed = 30;
        this.angularVel = 0;

        this.genome = genome;
        this.engine = new Engine(EngineType.fromGene(genome[GEN_Engine]));
        this.shipPhysics = new ShipPhysics(this, this);

        Gdx.app.log("SHIP_DEBUG", "Длина: " + genome[GEN_Length] + " Ширина: " + genome[GEN_Width] + " Угол: " + genome[GEN_BowAngle]);
        Gdx.app.log("SHIP_DEBUG", "Ген двигателя: " + genome[GEN_Engine]);
        Gdx.app.log("SHIP_DEBUG", "Тип двигателя: " + EngineType.fromGene(genome[GEN_Engine]));
    }

    public void shipUpdate(float deltaTime) {
        shipPhysics.update(deltaTime);
    }

//    ДВИГАТЕЛИ

    public enum EngineType {
        STEAM(0.0f, 0.25f, 70, 0.4f, 0.9f, "Паровой"),
        DIESEL(0.25f, 0.5f, 90, 0.6f, 0.8f, "Дизельный"),
        TURBINE(0.5f, 0.75f, 120, 0.8f, 0.6f, "Газотурбинный"),
        NUCLEAR(0.75f, 1.0f, 150, 0.7f, 0.4f, "Ядерный");

        public final float minGene, maxGene;
        public final float maxSpeed, acceleration, reliability;
        public final String name;

        EngineType(float minGene, float maxGene, float maxSpeed, float acceleration, float reliability, String name) {
            this.minGene = minGene;
            this.maxGene = maxGene;
            this.maxSpeed = maxSpeed;
            this.acceleration = acceleration;
            this.reliability = reliability;
            this.name = name;
        }

        public static EngineType fromGene(float geneValue) {
            // Обрабатываем граничный случай 1.0
            if (geneValue == 1.0f) {
                return NUCLEAR;
            }

            for (EngineType type : values()) {
                if (geneValue >= type.minGene && geneValue < type.maxGene) {
                    return type;
                }
            }
            return STEAM;
        }

    }

    public class Engine {
        private EngineType type;
        private boolean operational = true;

        public Engine(EngineType type) {
            this.type = type;
        }

        public float getMaxSpeed() {
            return type.maxSpeed;
        }

        public float getAcceleration() {
            return type.acceleration;
        }

        public float getReliability() {
            return type.reliability;
        }

        public String getName() {
            return type.name;
        }

        public boolean isOperational() {
            return operational;
        }
    }


//    ИНТЕРФЕЙС ShipPhysProp


//    ГЕТТЕРЫ ИНФЫ ИЗ ГЕНОВ

    public float getLength() {
        return MathUtils.map(genome[GEN_Length], 0, 1, MIN_LENGTH, MAX_LENGTH);
    }

    public float getWidth() {
        return MathUtils.map(genome[GEN_Width], 0, 1, MIN_WIDTH, MAX_WIDTH);
    }

    public float getBowAngle() {
        return MathUtils.map(genome[GEN_BowAngle], 0F, 1F, MIN_BOW_ANGLE, MAX_BOW_ANGLE);
    }

    @Override
    public float getAcceleration() {
        return engine.getAcceleration();
    }

    public float getMaxSpeed() {
        return engine.getMaxSpeed();
    }

    public float getX() {
        return x;
    }

    //    СЕТТЕРЫ
    @Override
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAngularVel() {
        return angularVel;
    }

    @Override
    public void setAngularVel(float angularVel) {
        this.angularVel = angularVel;
    }

    public Engine getEngine() {
        return engine;
    }

}
