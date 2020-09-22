package rpgbattle.model;

public class GameCharacter {
    private final String name;
    private int hitPoints, energy;
    private final Weapon weapon;

    public GameCharacter(String name, int hitPoints, int energy, Weapon weapon) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.energy = energy;
        this.weapon = weapon;
    }

    public String attack(GameCharacter other) {
        return weapon.attack(this, other);
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

    public Weapon getWeapon() {
        return weapon;
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
