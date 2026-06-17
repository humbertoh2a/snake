package com.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class GameScreen extends ScreenAdapter {
    private static final int COLUMNS = 20;
    private static final int ROWS = 15;
    private static final float MOVE_INTERVAL = 0.18f;

    private final SnakeGame game;
    private final Snake snake;
    private float moveTimer;

    GameScreen(SnakeGame game) {
        this.game = game;
        this.snake = new Snake(COLUMNS / 2, ROWS / 2);
    }

    @Override
    public void render(float delta) {
        handleInput();
        update(delta);

        Gdx.gl.glClearColor(0.08f, 0.18f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        BoardMetrics board = getBoardMetrics();
        drawBoard(board);
        drawSnake(board);
        drawHud();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            snake.turn(Direction.UP);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            snake.turn(Direction.DOWN);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            snake.turn(Direction.LEFT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            snake.turn(Direction.RIGHT);
        }
    }

    private void update(float delta) {
        moveTimer += delta;

        if (moveTimer >= MOVE_INTERVAL) {
            snake.move(COLUMNS, ROWS);
            moveTimer = 0f;
        }
    }

    private BoardMetrics getBoardMetrics() {
        float availableWidth = Gdx.graphics.getWidth() - 80f;
        float availableHeight = Gdx.graphics.getHeight() - 120f;
        float cellSize = Math.min(availableWidth / COLUMNS, availableHeight / ROWS);
        float boardWidth = cellSize * COLUMNS;
        float boardHeight = cellSize * ROWS;
        float startX = (Gdx.graphics.getWidth() - boardWidth) / 2f;
        float startY = 48f;

        return new BoardMetrics(startX, startY, cellSize, boardWidth, boardHeight);
    }

    private void drawBoard(BoardMetrics board) {
        ShapeRenderer shapes = game.getShapeRenderer();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.11f, 0.27f, 0.16f, 1f);
        shapes.rect(board.startX, board.startY, board.width, board.height);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.18f, 0.36f, 0.22f, 1f);

        for (int x = 0; x <= COLUMNS; x++) {
            float lineX = board.startX + x * board.cellSize;
            shapes.line(lineX, board.startY, lineX, board.startY + board.height);
        }

        for (int y = 0; y <= ROWS; y++) {
            float lineY = board.startY + y * board.cellSize;
            shapes.line(board.startX, lineY, board.startX + board.width, lineY);
        }

        shapes.end();
    }

    private void drawSnake(BoardMetrics board) {
        ShapeRenderer shapes = game.getShapeRenderer();

        shapes.begin(ShapeRenderer.ShapeType.Filled);

        for (GridPosition part : snake.getBody()) {
            boolean isHead = part == snake.getHead();
            shapes.setColor(isHead ? new Color(0.61f, 0.91f, 0.43f, 1f) : new Color(0.34f, 0.75f, 0.29f, 1f));
            shapes.rect(
                board.startX + part.x * board.cellSize + 2f,
                board.startY + part.y * board.cellSize + 2f,
                board.cellSize - 4f,
                board.cellSize - 4f
            );
        }

        shapes.end();
    }

    private void drawHud() {
        SpriteBatch batch = game.getBatch();
        BitmapFont font = game.getFont();

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "Use arrow keys", 24f, Gdx.graphics.getHeight() - 24f);
        batch.end();
    }

    private static class BoardMetrics {
        final float startX;
        final float startY;
        final float cellSize;
        final float width;
        final float height;

        BoardMetrics(float startX, float startY, float cellSize, float width, float height) {
            this.startX = startX;
            this.startY = startY;
            this.cellSize = cellSize;
            this.width = width;
            this.height = height;
        }
    }
}
