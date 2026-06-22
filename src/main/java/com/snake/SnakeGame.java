package com.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

// main app class. it owns shared drawing tools and starts on the menu screen.
public class SnakeGame extends Game {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private HighScoreManager highScoreManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(2f);
        highScoreManager = new HighScoreManager();

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }

        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

    SpriteBatch getBatch() {
        return batch;
    }

    ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    BitmapFont getFont() {
        return font;
    }

    HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }
}

class MainMenuScreen extends ScreenAdapter {
    private static final float BUTTON_WIDTH = 220f;
    private static final float BUTTON_HEIGHT = 56f;

    private final SnakeGame game;
    private final GlyphLayout layout = new GlyphLayout();
    private final Rectangle newGameButton = new Rectangle();
    private final Rectangle highScoresButton = new Rectangle();
    private final Rectangle exitButton = new Rectangle();
    private final Vector3 mousePosition = new Vector3();

    MainMenuScreen(SnakeGame game) {
        this.game = game;
        updateButtonPositions();
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.12f, 0.25f, 0.16f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawButtons();
        drawText();
    }

    @Override
    public void resize(int width, int height) {
        updateButtonPositions();
    }

    private void handleInput() {
        if (!Gdx.input.justTouched()) {
            return;
        }

        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
        mousePosition.y = Gdx.graphics.getHeight() - mousePosition.y;

        if (newGameButton.contains(mousePosition.x, mousePosition.y)) {
            game.setScreen(new GameScreen(game));
        } else if (highScoresButton.contains(mousePosition.x, mousePosition.y)) {
            game.setScreen(new HighScoreScreen(game));
        } else if (exitButton.contains(mousePosition.x, mousePosition.y)) {
            Gdx.app.exit();
        }
    }

    private void updateButtonPositions() {
        float centerX = Gdx.graphics.getWidth() / 2f - BUTTON_WIDTH / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        newGameButton.set(centerX, centerY + 10f, BUTTON_WIDTH, BUTTON_HEIGHT);
        highScoresButton.set(centerX, centerY - 62f, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.set(centerX, centerY - 134f, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void drawButtons() {
        ShapeRenderer shapes = game.getShapeRenderer();
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.32f, 0.62f, 0.31f, 1f);
        shapes.rect(newGameButton.x, newGameButton.y, newGameButton.width, newGameButton.height);
        shapes.setColor(0.28f, 0.52f, 0.33f, 1f);
        shapes.rect(highScoresButton.x, highScoresButton.y, highScoresButton.width, highScoresButton.height);
        shapes.setColor(0.24f, 0.43f, 0.25f, 1f);
        shapes.rect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);
        shapes.end();
    }

    private void drawText() {
        SpriteBatch batch = game.getBatch();
        BitmapFont font = game.getFont();

        batch.begin();
        font.setColor(Color.WHITE);
        drawCenteredText(batch, font, "SNAKE", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + 130f);
        drawCenteredText(batch, font, "New Game", newGameButton.x + newGameButton.width / 2f, newGameButton.y + 36f);
        drawCenteredText(batch, font, "High Scores", highScoresButton.x + highScoresButton.width / 2f, highScoresButton.y + 36f);
        drawCenteredText(batch, font, "Exit", exitButton.x + exitButton.width / 2f, exitButton.y + 36f);
        batch.end();
    }

    private void drawCenteredText(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        layout.setText(font, text);
        font.draw(batch, text, x - layout.width / 2f, y);
    }
}
