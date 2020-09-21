package rpgbattle.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.macalester.graphics.CanvasWindow;
import rpgbattle.model.GameCharacter;

public class Game {
    private static final String GAME_TITLE = "Final MacBattle XIV: Bagpipe of Time";

    private List<GameCharacter> playerCharacters, enemies;
    private int level;

    public static void main(String[] args) {
        new Game().play();
    }

    private Game() {
        CanvasWindow canvas = new CanvasWindow(GAME_TITLE, 800, 600);

        playerCharacters = new ArrayList<>();
        playerCharacters.add(new GameCharacter("Scottie", 500, 0, 10, 15, 0, 0));
        playerCharacters.add(new GameCharacter("President", 400, 40, 0, 0, 100, 10));
        playerCharacters.add(new GameCharacter("Provost", 300, 0, 20, 40, 0, 0));

        enemies = List.of();  // triggers immediate level up when game begins
    }

    private void play() {
        System.out.println();
        System.out.println("Welcome to " + GAME_TITLE + "!");
        try (Scanner scanner = new Scanner(System.in)) {
            if (areAllEnemiesDefeated()) {
                levelUp();
            }
        }
    }

    private boolean areAllEnemiesDefeated() {
        for (GameCharacter enemy : enemies) {
            if (enemy.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private void levelUp() {
        level++;
        System.out.println("–––––– LEVEL " + level + " ––––––");
    }
}
