package io.github.GeneShips;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.GeneShips.entities.Ship;
import io.github.GeneShips.generators.ShipGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {

    private float time;

    private ShipRenderer shipRenderer;
    private ShipGenerator shipGenerator;
    private ShapeRenderer shapeRenderer;
    private List<Ship> ships;

    public float getTime() {
        return time;
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        shipGenerator = new ShipGenerator();
        shipRenderer = new ShipRenderer();
        ships = new ArrayList<>();

        Gdx.app.log("Main", "Generator has started!");
    }

    @Override
    public void render() {
        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            Ship newShip = shipGenerator.createRandomShip(mouseX, mouseY);
            ships.add(newShip);
        }

        for (Ship ship : ships) {
            ship.shipUpdate(deltaTime);
        }

        for (Ship ship : ships) {
            shipRenderer.renderShip(ship, shapeRenderer);
        }


    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
