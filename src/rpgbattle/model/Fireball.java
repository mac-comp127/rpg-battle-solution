package rpgbattle.model;

public class Fireball implements Weapon {
    private final int damage;
    private final int energyRequired;

    public Fireball(int damage, int energyRequired) {
        this.damage = damage;
        this.energyRequired = energyRequired;
    }    

    public String getDescription() {
        return "Fireball (" + energyRequired + " ⚡️ → " + damage + " ☠️)";
    }

    public String attack(GameCharacter attacker, GameCharacter target) {
        if (attacker.useEnergy(energyRequired)) {
            target.takeDamage(damage);
            return attacker.getName() + " hit " + target.getName() + " with a fireball for "
                + damage + " points of damage";
        } else {
            return attacker.getName() + " did not have enough energy for a fireball attack";
        }
    }
}
