package com.snake;

enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    final int dx;
    final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    boolean isOpposite(Direction other) {
        return dx + other.dx == 0 && dy + other.dy == 0;
    }
}
