package rpgbattle.model;

public interface Weapon {
    String getDescription();
    String attack(GameCharacter attacker, GameCharacter target);
}
