package rpgbattle.model;

import java.util.Random;

public class Sword implements Weapon {
    private static final Random rand = new Random();

    private final int minDamage, maxDamage;

    public Sword(int minDamage, int maxDamage) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }    

    public String getDescription() {
        if (minDamage == maxDamage ) {
            return "Sword (" + minDamage + " ☠️)";
        } else {
            return "Sword (" + minDamage + "–" + maxDamage + " ☠️)";
        }
    }

    public String attack(GameCharacter attacker, GameCharacter target) {
        int damage = rand.nextInt(maxDamage - minDamage + 1) + minDamage;
        target.takeDamage(damage);
        return attacker.getName() + " struck " + target.getName() + " with a sword for "
            + damage + " points of damage";
    }
}
