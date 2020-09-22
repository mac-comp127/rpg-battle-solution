package rpgbattle.ui;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import rpgbattle.model.GameCharacter;

public class Game {
    private static final String GAME_TITLE = "Final MacBattle XIV: Bagpipe of Time";
    private static final Random rand = new Random();

    private List<GameCharacter> playerParty, enemyParty;
    private int level;

    public static void main(String[] args) {
        new Game().play();
    }

    private Game() {
        // CanvasWindow canvas = new CanvasWindow(GAME_TITLE, 800, 600);

        playerParty = new ArrayList<>();
        playerParty.add(new GameCharacter("Scottie", 500, 0, 10, 15, 0, 0));
        playerParty.add(new GameCharacter("President", 400, 40, 0, 0, 100, 10));
        playerParty.add(new GameCharacter("Provost", 300, 0, 20, 40, 0, 0));

        level = 0;
        enemyParty = List.of();  // empty list triggers immediate level-up when game begins
    }

    private void play() {
        System.out.println();
        System.out.println("Welcome to " + GAME_TITLE + "!");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                playRound(scanner);

                if (!anyAlive(playerParty)) {
                    System.err.println("☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️");
                    System.out.println("☠️ You were defeated on level " + level + " ☠️");
                    System.err.println("☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️☠️");
                    System.out.println();
                    System.err.println("Your valor and bravery will be remembered");
                    System.err.println("until all current students have graduated.");
                    System.out.println();
                    break;
                }
            }
        }
    }

    private void playRound(Scanner scanner) {
        if (!anyAlive(enemyParty)) {
            levelUp();
        }

        System.out.println("Your party:");
        printGameCharacters(playerParty, 1);

        System.out.println("Enemies:");
        printGameCharacters(enemyParty, playerParty.size() + 1);

        if (playersAliveInBothParties()) {
            GameCharacter attacker = selectPlayer(scanner, "attacker", playerParty, 1);
            GameCharacter target = selectPlayer(scanner, "target", enemyParty, playerParty.size() + 1);
            showAttack(attacker, target);
        }

        if (playersAliveInBothParties()) {
            showAttack(
                selectRandom(enemyParty),
                selectRandom(playerParty));
        }

        for (GameCharacter player : playerParty) {
            player.recover();
        }
    }

    private boolean playersAliveInBothParties() {
        return anyAlive(playerParty) && anyAlive(enemyParty);
    }

    private void printGameCharacters(List<GameCharacter> characters, int startIndex) {
        int i = startIndex;
        for (GameCharacter character : characters) {
            System.out.printf("    %d. %-20s", i++, character.getName());
            
            if (character.isAlive()) {
                System.out.printf(
                    "%3d ❤️ / %3d ⚡️  |  %s\n",
                    character.getHitPoints(),
                    character.getEnergy(),
                    character.getWeaponDescription());
            } else {
                System.out.println("DEFEATED");
            }
        }
        System.out.println();
    }

    private GameCharacter selectPlayer(Scanner scanner, String prompt, List<GameCharacter> characters, int startIndex) {
        while (true) {
            System.out.print("Select " + prompt + ": ");
            int playerNum = scanner.nextInt() - startIndex;
            if (playerNum >= 0 && playerNum < characters.size()) {
                GameCharacter character = characters.get(playerNum);
                if (character.isAlive()) {
                    return character;
                } else {
                    System.out.println(character.getName() + " is already defeated");
                }
            }
            System.out.println("Please enter a number from " + startIndex + " to " + (startIndex + characters.size() - 1));
        }
    }

    private GameCharacter selectRandom(List<GameCharacter> characters) {
        List<GameCharacter> alive = characters.stream().filter(GameCharacter::isAlive).collect(toList());
        return alive.get(rand.nextInt(alive.size()));
    }

    private void showAttack(GameCharacter attacker, GameCharacter target) {
        System.out.println(attacker.attack(target));
        if (!target.isAlive()) {
            System.out.println(target.getName().toUpperCase() + " DEFEATED");
        }
        System.out.println();
    }

    private boolean anyAlive(List<GameCharacter> characters) {
        for (GameCharacter character : characters) {
            if (character.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private void levelUp() {
        level++;
        System.out.println();
        System.out.println("–––––– LEVEL " + level + " ––––––");
        System.out.println();

        enemyParty = new ArrayList<>();
        for (int n = randInt(3, 5); n > 0; n--) {
            enemyParty.add(generateEnemy());
        }
    }

    private GameCharacter generateEnemy() {
        int minAttack = randInt(10, 15 + level * 2);
        if (randInt(0, 5) == 0) {
            return new GameCharacter(  // fireball attack
                generateRandomName(),
                randInt(10, 20 + level * 4),
                10 + level * 2,
                0, 0, 
                minAttack * 3,
                randInt(10, 20));
        } else {
            return new GameCharacter(  // sword attack
                generateRandomName(),
                randInt(10, 20 + level * 4),
                0,
                minAttack,
                minAttack + randInt(0, level),
                0, 0);
        }
    }

    private String generateRandomName() {
        StringBuffer name = new StringBuffer();
        boolean capitalizeNext = true;
        for (int c = randInt(2, 7); c > 0; c--) {
            char consonant = randChar("bcdfghjklmnpqrstvwxz");
            if (capitalizeNext) {
                consonant = Character.toUpperCase(consonant);
                capitalizeNext = false;
            }
            name.append(consonant);
            name.append(randChar("aeiouyaeiouyāēīōů"));
            if (randInt(0, 5) < 2 && c > 1) {
                name.append(" ");
                capitalizeNext = true;
            }
        }
        return name.toString();
    }

    private char randChar(String string) {
        return string.charAt(rand.nextInt(string.length()));
    }

    private static final int randInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
}
