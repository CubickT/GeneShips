package io.github.GeneShips;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import io.github.GeneShips.Renderers.ShipRenderer;
import io.github.GeneShips.entities.Ship;
import io.github.GeneShips.entities.ShipPhysics;
import io.github.GeneShips.generators.ShipBodyFactory;
import io.github.GeneShips.generators.ShipGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {

    private float time;

    private ShipBodyFactory shipBodyFactory;
    private ShipPhysics shipPhysics;
    private ShipRenderer shipRenderer;
    private ShipGenerator shipGenerator;
    private ShapeRenderer shapeRenderer;
    private List<Ship> ships;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    public float getTime() {
        return time;
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void create() {
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shipBodyFactory = new ShipBodyFactory(world);

        shapeRenderer = new ShapeRenderer();
        shipGenerator = new ShipGenerator(shipBodyFactory);
        shipRenderer = new ShipRenderer();
        ships = new ArrayList<>();

        Gdx.app.log("Main", "Generator has started!");
    }

    @Override
    public void render() {
        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60F, 6, 2);
        camera.update();
        debugRenderer.render(world, camera.combined.scl(1f / ShipBodyFactory.PIXELS_TO_METERS));

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
        world.dispose();
        debugRenderer.dispose();
    }
}

