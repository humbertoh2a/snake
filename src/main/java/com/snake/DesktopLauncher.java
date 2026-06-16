package com.snake;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// desktop entry point, just boots up the libgdx app
public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Snake");
        config.setWindowedMode(640, 480);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new SnakeGame(), config);
    }
}
