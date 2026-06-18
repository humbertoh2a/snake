package com.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class GameOverScreen extends ScreenAdapter {
    private final SnakeGame game;
    private final GlyphLayout layout = new GlyphLayout();

    GameOverScreen(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.13f, 0.08f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = game.getBatch();
        BitmapFont font = game.getFont();

        batch.begin();
        font.setColor(Color.WHITE);
        drawCenteredText(batch, font, "Game Over", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + 28f);
        drawCenteredText(batch, font, "Press Enter", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 28f);
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void drawCenteredText(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        layout.setText(font, text);
        font.draw(batch, text, x - layout.width / 2f, y);
    }
}
