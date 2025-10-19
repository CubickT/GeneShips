package io.github.GeneShips;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import io.github.GeneShips.generators.ShipGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private float time;
    private ShipGenerator shipGenerator;

    private List<float[]> ships;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        Gdx.app.log("Main", "Процедурный генератор кораблей запущен!");
        shipGenerator = new ShipGenerator();
        ships = new ArrayList<>();
    }

    @Override
    public void render() {
        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        time += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float length = MathUtils.random(70, 200);
            float width = MathUtils.random(40, 100);
            float bowAngle = MathUtils.random(0F, 1F);

            Gdx.app.log("SHIP_PARAMS", "Длина: " + length + ", Ширина: " + width + ", Угол: " + bowAngle);
            float[] ship = {mouseX, mouseY, length, width, bowAngle};
            ships.add(ship);

        }

        for (float[] ship : ships) {
            float x = ship[0];
            float y = ship[1];
            float length = ship[2];
            float width = ship[3];
            float bowAngle = ship[4];

            shipGenerator.renderShip(x, y, length, width, bowAngle, shapeRenderer);
        }

    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
