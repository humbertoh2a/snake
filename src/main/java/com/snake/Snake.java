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

    GridPosition getNextHead() {
        return getHead().move(direction);
    }

    void move(int columns, int rows, boolean grow) {
        GridPosition nextHead = getNextHead();

        // walls will wrap later, so for now the snake just waits at the edge.
        if (nextHead.x < 0 || nextHead.x >= columns || nextHead.y < 0 || nextHead.y >= rows) {
            return;
        }

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

    List<GridPosition> getBody() {
        return Collections.unmodifiableList(body);
    }
}
