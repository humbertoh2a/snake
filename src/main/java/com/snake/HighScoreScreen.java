package com.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

class HighScoreScreen extends ScreenAdapter {
    private final SnakeGame game;
    private final List<Integer> scores;
    private final GlyphLayout layout = new GlyphLayout();

    HighScoreScreen(SnakeGame game) {
        this.game = game;
        this.scores = game.getHighScoreManager().loadScores();
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.08f, 0.18f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawText();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()
            || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
            || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void drawText() {
        SpriteBatch batch = game.getBatch();
        BitmapFont font = game.getFont();
        float centerX = Gdx.graphics.getWidth() / 2f;
        float startY = Gdx.graphics.getHeight() / 2f + 150f;

        batch.begin();
        font.setColor(Color.WHITE);
        drawCenteredText(batch, font, "High Scores", centerX, startY);

        if (scores.isEmpty()) {
            drawCenteredText(batch, font, "No scores yet", centerX, startY - 72f);
        } else {
            // draw only what the manager returns, already sorted as top scores
            for (int i = 0; i < scores.size(); i++) {
                drawCenteredText(batch, font, (i + 1) + ". " + scores.get(i), centerX, startY - 70f - i * 42f);
            }
        }

        drawCenteredText(batch, font, "Press Enter to return", centerX, 82f);
        batch.end();
    }

    private void drawCenteredText(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        layout.setText(font, text);
        font.draw(batch, text, x - layout.width / 2f, y);
    }
}
