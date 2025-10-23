package io.github.GeneShips.Interfaces;

import io.github.GeneShips.entities.Ship;

public interface ShipPhysProp {

    float getX();

    float getY();

    //    ФИЗИКА
    float getSpeed();

    float getAngularVel();

    float getRotation();

    //    ГЕОМЕТРИЯ
    float getLength();

    float getWidth();

    float getBowAngle();

    //    ГЕНЫ

    float getAcceleration();

    Ship.Engine getEngine();

}
