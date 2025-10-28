package io.github.GeneShips.generators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.GeneShips.MathUtils;
import io.github.GeneShips.entities.Ship;

public class ShipBodyFactory {
    public static final float PIXELS_TO_METERS = 0.01f;
    private final World world;

    public ShipBodyFactory(World world) {
        this.world = world;
    }


    public Body createShipBody(Ship ship) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
            ship.getX() * PIXELS_TO_METERS,
            ship.getY() * PIXELS_TO_METERS
        );

        Body body = world.createBody(bodyDef);


        createHullFixtures(body, ship);

        body.setUserData(ship);
        return body;
    }

    private void createHullFixtures(Body body, Ship ship) {
        float length = ship.getLength();
        float width = ship.getWidth();
        float bowAngle = ship.getBowAngle();

        float noseWidth = MathUtils.map(bowAngle, 0, 1, width * 1F, width * 0.6F);
        float noseLength = MathUtils.map(bowAngle, 0, 1, 20, 50);

        // ПЕРЕДНЯЯ ЧАСТЬ (выпуклая) - 4 вершины
        Vector2[] frontVertices = new Vector2[4];
        frontVertices[0] = new Vector2(0.5F * length + noseLength, 0);
        frontVertices[1] = new Vector2(0.5F * length + 0.5F * noseLength, noseWidth);
        frontVertices[2] = new Vector2(0.5F * length, width);
        frontVertices[3] = new Vector2(0.5F * length, -width);

        // ЗАДНЯЯ ЧАСТЬ (выпуклая) - 4 вершины
        Vector2[] rearVertices = new Vector2[4];
        rearVertices[0] = new Vector2(0.5F * length, width);
        rearVertices[1] = new Vector2(-0.5F * length, width * 0.7F);
        rearVertices[2] = new Vector2(-0.5F * length, -width * 0.7F);
        rearVertices[3] = new Vector2(0.5F * length, -width);


        for (Vector2 vertex : frontVertices) {
            vertex.scl(PIXELS_TO_METERS);
        }
        for (Vector2 vertex : rearVertices) {
            vertex.scl(PIXELS_TO_METERS);
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;


        PolygonShape frontShape = new PolygonShape();
        frontShape.set(frontVertices);
        fixtureDef.shape = frontShape;
        body.createFixture(fixtureDef);


        PolygonShape rearShape = new PolygonShape();
        rearShape.set(rearVertices);
        fixtureDef.shape = rearShape;
        body.createFixture(fixtureDef);


        frontShape.dispose();
        rearShape.dispose();
    }
}
