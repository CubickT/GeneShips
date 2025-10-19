package io.github.GeneShips.generators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class ShipGenerator {
    float length, width;
    float bowAngle;


    private float map(float value, float rangeS, float rangeE, float enRangeS, float enRangeE) {
        return enRangeS + (enRangeE - enRangeS) * ((value - rangeS) / (rangeE - rangeS));
    }

    public void generateShipAt(float x, float y, ShapeRenderer shapeRenderer) {


        renderShip(x, y, length, width, bowAngle, shapeRenderer);
    }

    public void renderShip(float x, float y, float length, float width, float bowAngle, ShapeRenderer shapeRenderer) {
        float noseWidth = map(bowAngle, 0, 1, width * 1.7F, width * 0.6F);
        float noseLength = map(bowAngle, 0, 1, 40, 100);

        float[][] vertices = {
            {0, 0},                            // Нос
            // Правый борт
            {-noseLength * 0.5F, noseWidth},     // Правый угол носа
            {-noseLength, width},              // Правый передний угол корпуса
            {-length * 0.8F, width},                  // Правый задний угол корпуса
            // Корма
            {-length, width * 0.7F},          // Правый угол кормы
            {-length, -width * 0.7F},         // Левый угол кормы
            //Левый борт
            {-length * 0.8F, -width},                  // Левый задний угол корпуса
            {-noseLength, -width},                // Левый передний угол корпуса
            {-noseLength * 0.5F, -noseWidth},   // Левый угол носа
        };

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIGHT_GRAY);

        for (int i = 0; i < vertices.length; i++) {
            int next = (i + 1) % vertices.length;
            float x1 = x + vertices[i][0];
            float y1 = y + vertices[i][1];
            float x2 = x + vertices[next][0];
            float y2 = y + vertices[next][1];

            shapeRenderer.line(x1, y1, x2, y2);
        }
        shapeRenderer.end();
    }
}
