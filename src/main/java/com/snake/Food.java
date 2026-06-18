package com.snake;

import java.util.Random;

class Food {
    private final Random random = new Random();
    private GridPosition position;

    Food(int columns, int rows, Snake snake) {
        respawn(columns, rows, snake);
    }

    void respawn(int columns, int rows, Snake snake) {
        GridPosition nextPosition;

        // keep trying until food lands outside the snake body
        do {
            nextPosition = new GridPosition(random.nextInt(columns), random.nextInt(rows));
        } while (snake.contains(nextPosition));

        position = nextPosition;
    }

    GridPosition getPosition() {
        return position;
    }
}
