package com.snake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Snake {
    private final List<GridPosition> body = new ArrayList<>();
    private Direction direction = Direction.RIGHT;

    Snake(int startX, int startY) {
        body.add(new GridPosition(startX, startY));
        body.add(new GridPosition(startX - 1, startY));
        body.add(new GridPosition(startX - 2, startY));
    }

    void turn(Direction nextDirection) {
        if (!direction.isOpposite(nextDirection)) {
            direction = nextDirection;
        }
    }

    GridPosition getNextHead(int columns, int rows) {
        return getHead().move(direction).wrap(columns, rows);
    }

    void move(int columns, int rows, boolean grow) {
        // leaving one side of the board brings the head back on the opposite side
        GridPosition nextHead = getNextHead(columns, rows);

        body.add(0, nextHead);

        // growing means keeping the tail for one move
        if (!grow) {
            body.remove(body.size() - 1);
        }
    }

    GridPosition getHead() {
        return body.get(0);
    }

    boolean contains(GridPosition position) {
        return body.contains(position);
    }

    boolean willHitItself(GridPosition nextHead, boolean grow) {
        int lastIndex = grow ? body.size() : body.size() - 1;

        // when not growing, the tail moves away, so that cell is still safe
        for (int i = 0; i < lastIndex; i++) {
            if (body.get(i).equals(nextHead)) {
                return true;
            }
        }

        return false;
    }

    List<GridPosition> getBody() {
        return Collections.unmodifiableList(body);
    }
}
