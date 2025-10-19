package io.github.GeneShips;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private float time;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        Gdx.app.log("Main", "Процедурный генератор кораблей запущен!");
    }

    @Override
    public void render() {
        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        time += Gdx.graphics.getDeltaTime();

        // Простая отрисовка для теста
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);

        // Движущийся прямоугольник - проверка что всё работает
        float x = 100 + (float) Math.sin(time) * 50;
        float y = 100;
        shapeRenderer.rect(x, y, 80, 40);

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
