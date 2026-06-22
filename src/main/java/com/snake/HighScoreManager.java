package com.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class HighScoreManager {
    private static final int MAX_SCORES = 5;
    private static final String FILE_NAME = "highscores.txt";

    private final FileHandle scoreFile;

    HighScoreManager() {
        scoreFile = Gdx.files.local(FILE_NAME);
    }

    List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();

        if (!scoreFile.exists()) {
            return scores;
        }

        String[] lines = scoreFile.readString().split("\\R");

        for (String line : lines) {
            try {
                scores.add(Integer.parseInt(line.trim()));
            } catch (NumberFormatException ignored) {
                // skip weird lines so one bad value does not break the file
            }
        }

        sortAndTrim(scores);
        return scores;
    }

    void saveScore(int score) {
        List<Integer> scores = loadScores();
        scores.add(score);
        sortAndTrim(scores);
        writeScores(scores);
    }

    private void sortAndTrim(List<Integer> scores) {
        Collections.sort(scores, Collections.reverseOrder());

        while (scores.size() > MAX_SCORES) {
            scores.remove(scores.size() - 1);
        }
    }

    private void writeScores(List<Integer> scores) {
        StringBuilder builder = new StringBuilder();

        for (int score : scores) {
            builder.append(score).append(System.lineSeparator());
        }

        scoreFile.writeString(builder.toString(), false);
    }
}
