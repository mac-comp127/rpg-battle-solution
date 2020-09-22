package rpgbattle.model;

import java.util.Random;

public class GameCharacter {
    private static final Random rand = new Random();

    private final String name;
    private int hitPoints, energy;
    private final int swordMinDamage, swordMaxDamage;
    private final int fireballDamage;
    private final int fireballEnergyRequired;

    public GameCharacter(String name, int hitPoints, int energy, int swordMinDamage, int swordMaxDamage, int fireballDamage, int fireballEnergyRequired) {
        if ((swordMinDamage != 0 || swordMaxDamage != 0) && (fireballDamage != 0 || fireballEnergyRequired != 0)) {
            throw new IllegalArgumentException("Character cannot have both sword and fireball damage");
        }
        this.name = name;
        this.hitPoints = hitPoints;
        this.energy = energy;
        this.swordMinDamage = swordMinDamage;
        this.swordMaxDamage = swordMaxDamage;
        this.fireballDamage = fireballDamage;
        this.fireballEnergyRequired = fireballEnergyRequired;
    }

    public String getWeaponDescription() {
        if (fireballDamage > 0) {
            return "Fireball (" + fireballEnergyRequired + " ⚡️ → " + fireballDamage + " ☠️)";
        } else {
            if (swordMinDamage == swordMaxDamage ) {
                return "Sword (" + swordMinDamage + " ☠️)";
            } else {
                return "Sword (" + swordMinDamage + "–" + swordMaxDamage + " ☠️)";
            }
        }
    }

    public String attack(GameCharacter target) {
        if (fireballDamage > 0) {
            if (this.useEnergy(fireballEnergyRequired)) {
                target.takeDamage(fireballDamage);
                return this.getName() + " hit " + target.getName() + " with a fireball for "
                    + fireballDamage + " points of damage";
            } else {
                return this.getName() + " did not have enough energy for a fireball attack";
            }
        } else {
            int damage = rand.nextInt(swordMaxDamage - swordMinDamage + 1) + swordMinDamage;
            target.takeDamage(damage);
            return this.getName() + " struck " + target.getName() + " with a sword for "
                + damage + " points of damage";
        }
    }

    public String getName() {
        return name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getEnergy() {
        return energy;
    }

    public void takeDamage(int damage) {
        hitPoints = Math.max(0, hitPoints - damage);
    }

    public boolean useEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        } else {
            return false;
        }
    }

    public void recover() {
        if (isAlive()) {
            energy++;
            hitPoints++;
        }
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }
}
