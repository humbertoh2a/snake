package com.snake;

class GridPosition {
    final int x;
    final int y;

    GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    GridPosition move(Direction direction) {
        return new GridPosition(x + direction.dx, y + direction.dy);
    }
}
