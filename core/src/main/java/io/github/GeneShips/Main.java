package io.github.GeneShips;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.GeneShips.generators.ShipGenerator;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private float time;
    private ShipGenerator shipGenerator;


    public void create() {
        shapeRenderer = new ShapeRenderer();
        Gdx.app.log("Main", "Процедурный генератор кораблей запущен!");
        shipGenerator = new ShipGenerator();
    }

    public void render() {
        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        time += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            shipGenerator.generateShipAt(mouseX, mouseY);
        }

    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
