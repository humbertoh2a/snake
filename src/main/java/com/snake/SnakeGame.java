package com.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

// main app class. for now it just clears the screen to a dark green
// so we know the window + render loop actually work. real game comes next.
public class SnakeGame extends ApplicationAdapter {

    @Override
    public void create() {
        // nothing yet
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.35f, 0.15f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        // nothing to clean up yet
    }
}
