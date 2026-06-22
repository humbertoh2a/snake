package com.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

class GameOverScreen extends ScreenAdapter {
    private static final float BUTTON_WIDTH = 220f;
    private static final float BUTTON_HEIGHT = 56f;

    private final SnakeGame game;
    private final int finalScore;
    private final GlyphLayout layout = new GlyphLayout();
    private final Rectangle playAgainButton = new Rectangle();
    private final Rectangle exitButton = new Rectangle();
    private final Vector3 mousePosition = new Vector3();

    GameOverScreen(SnakeGame game, int finalScore) {
        this.game = game;
        this.finalScore = finalScore;
        updateButtonPositions();
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.13f, 0.08f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawButtons();
        drawText();
    }

    @Override
    public void resize(int width, int height) {
        updateButtonPositions();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            return;
        }

        if (!Gdx.input.justTouched()) {
            return;
        }

        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
        mousePosition.y = Gdx.graphics.getHeight() - mousePosition.y;

        if (playAgainButton.contains(mousePosition.x, mousePosition.y)) {
            game.setScreen(new GameScreen(game));
        } else if (exitButton.contains(mousePosition.x, mousePosition.y)) {
            Gdx.app.exit();
        }
    }

    private void updateButtonPositions() {
        float centerX = Gdx.graphics.getWidth() / 2f - BUTTON_WIDTH / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        playAgainButton.set(centerX, centerY - 30f, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.set(centerX, centerY - 102f, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void drawButtons() {
        ShapeRenderer shapes = game.getShapeRenderer();
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.32f, 0.62f, 0.31f, 1f);
        shapes.rect(playAgainButton.x, playAgainButton.y, playAgainButton.width, playAgainButton.height);
        shapes.setColor(0.24f, 0.43f, 0.25f, 1f);
        shapes.rect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);
        shapes.end();
    }

    private void drawText() {
        SpriteBatch batch = game.getBatch();
        BitmapFont font = game.getFont();

        batch.begin();
        font.setColor(Color.WHITE);
        drawCenteredText(batch, font, "Game Over", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + 112f);
        drawCenteredText(batch, font, "Final Score: " + finalScore, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + 58f);
        drawCenteredText(batch, font, "Play Again", playAgainButton.x + playAgainButton.width / 2f, playAgainButton.y + 36f);
        drawCenteredText(batch, font, "Exit", exitButton.x + exitButton.width / 2f, exitButton.y + 36f);
        batch.end();
    }

    private void drawCenteredText(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        layout.setText(font, text);
        font.draw(batch, text, x - layout.width / 2f, y);
    }
}
