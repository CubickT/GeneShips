package io.github.GeneShips.Renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.GeneShips.entities.Ship;

import static io.github.GeneShips.MathUtils.map;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ShipRenderer {

    public void renderShip(Ship ship, ShapeRenderer shapeRenderer) {

        float x = ship.getX();
        float y = ship.getY();

        float length = ship.getLength();
        float width = ship.getWidth();
        float bowAngle = ship.getBowAngle();
        float rotation = ship.getRotation();

        float noseWidth = map(bowAngle, 0, 1, width * 1F, width * 0.6F);
        float noseLength = map(bowAngle, 0, 1, 20, 50);

        float[][] vertices = {
            {0.5F * length + noseLength, 0},                            // Нос
            // Правый борт
            {0.5F * length + 0.5F * noseLength, noseWidth},     // Правый угол носа
            {0.5F * length, width},              // Правый передний угол корпуса
            {-0.4F * length * 0.8F, width},                  // Правый задний угол корпуса
            // Корма
            {-0.5F * length, width * 0.7F},          // Правый угол кормы
            {-0.5F * length, -width * 0.7F},         // Левый угол кормы
            //Левый борт
            {-0.4F * length * 0.8F, -width},                  // Левый задний угол корпуса
            {0.5F * length, -width},                // Левый передний угол корпуса
            {0.5F * length + 0.5F * noseLength, -noseWidth},   // Левый угол носа
        };

        float[][] rotatedVertices = new float[vertices.length][2];
        for (int i = 0; i < vertices.length; i++) {
            rotatedVertices[i][0] = (float) (vertices[i][0] * cos(rotation) - vertices[i][1] * sin(rotation));
            rotatedVertices[i][1] = (float) (vertices[i][0] * sin(rotation) + vertices[i][1] * cos(rotation));
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        for (int i = 1; i < rotatedVertices.length - 1; i++) {
            shapeRenderer.triangle(
                x + rotatedVertices[0][0], y + rotatedVertices[0][1],  // Центральная точка (нос)
                x + rotatedVertices[i][0], y + rotatedVertices[i][1],   // Текущая вершина
                x + rotatedVertices[i + 1][0], y + rotatedVertices[i + 1][1]  // Следующая вершина
            );
        }

        shapeRenderer.end();

//        ВЕКТОР ДВИЖЕНИЯ
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(
            ship.getX(), ship.getY(),
            ship.getX() + (float) (ship.getSpeed() * cos(ship.getRotation()) * 10),
            ship.getY() + (float) (ship.getSpeed() * sin(ship.getRotation()) * 10)
        );
        shapeRenderer.end();
    }


}
