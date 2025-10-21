package io.github.GeneShips;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.GeneShips.entities.Ship;

public class ShipRenderer {

    private float map(float value, float rangeS, float rangeE, float enRangeS, float enRangeE) {
        return enRangeS + (enRangeE - enRangeS) * ((value - rangeS) / (rangeE - rangeS));
    }

    public void renderShip(Ship ship, ShapeRenderer shapeRenderer) {

        float x = ship.getX();
        float y = ship.getY();

        float length = ship.getLength();
        float width = ship.getWidth();
        float bowAngle = ship.getBowAngle();

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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        for (int i = 1; i < vertices.length - 1; i++) {
            shapeRenderer.triangle(
                x + vertices[0][0], y + vertices[0][1],  // Центральная точка (нос)
                x + vertices[i][0], y + vertices[i][1],   // Текущая вершина
                x + vertices[i + 1][0], y + vertices[i + 1][1]  // Следующая вершина
            );
        }

        shapeRenderer.end();
    }


}
