package com.snake;

import java.util.Objects;

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

    GridPosition wrap(int columns, int rows) {
        int wrappedX = (x + columns) % columns;
        int wrappedY = (y + rows) % rows;
        return new GridPosition(wrappedX, wrappedY);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof GridPosition)) {
            return false;
        }

        GridPosition position = (GridPosition) other;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
