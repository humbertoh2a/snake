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
    private final Food food;
    private float moveTimer;
    private int score;

    GameScreen(SnakeGame game) {
        this.game = game;
        this.snake = new Snake(COLUMNS / 2, ROWS / 2);
        this.food = new Food(COLUMNS, ROWS, snake);
    }

    @Override
    public void render(float delta) {
        handleInput();
        update(delta);

        Gdx.gl.glClearColor(0.08f, 0.18f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        BoardMetrics board = getBoardMetrics();
        drawBoard(board);
        drawFood(board);
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
            // check food before moving so the snake grows on this exact step
            GridPosition nextHead = snake.getNextHead(COLUMNS, ROWS);
            boolean ateFood = nextHead.equals(food.getPosition());

            if (snake.willHitItself(nextHead, ateFood)) {
                if (score > 0) {
                    game.getHighScoreManager().saveScore(score);
                }

                game.setScreen(new GameOverScreen(game, score));
                return;
            }

            snake.move(COLUMNS, ROWS, ateFood);

            if (ateFood) {
                // score is just the amount of food eaten
                score++;
                food.respawn(COLUMNS, ROWS, snake);
            }

            moveTimer = 0f;
        }
    }

    private BoardMetrics getBoardMetrics() {
        // keeps the grid centered even if the window size changes
        float availableWidth = Gdx.graphics.getWidth() - 120f;
        float availableHeight = Gdx.graphics.getHeight() - 150f;
        float cellSize = Math.min(availableWidth / COLUMNS, availableHeight / ROWS);
        float boardWidth = cellSize * COLUMNS;
        float boardHeight = cellSize * ROWS;
        float startX = (Gdx.graphics.getWidth() - boardWidth) / 2f;
        float startY = 56f;

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

        shapes.setColor(0.45f, 0.72f, 0.38f, 1f);
        shapes.rect(board.startX, board.startY, board.width, board.height);
        shapes.end();
    }

    private void drawFood(BoardMetrics board) {
        ShapeRenderer shapes = game.getShapeRenderer();
        GridPosition position = food.getPosition();
        float padding = board.cellSize * 0.18f;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.86f, 0.18f, 0.18f, 1f);
        shapes.circle(
            board.startX + position.x * board.cellSize + board.cellSize / 2f,
            board.startY + position.y * board.cellSize + board.cellSize / 2f,
            board.cellSize / 2f - padding
        );
        shapes.end();
    }

    private void drawSnake(BoardMetrics board) {
        ShapeRenderer shapes = game.getShapeRenderer();

        shapes.begin(ShapeRenderer.ShapeType.Filled);

        for (GridPosition part : snake.getBody()) {
            boolean isHead = part.equals(snake.getHead());
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
        font.draw(batch, "Score: " + score, 48f, Gdx.graphics.getHeight() - 36f);
        font.draw(batch, "Use arrow keys", 48f, Gdx.graphics.getHeight() - 72f);
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
