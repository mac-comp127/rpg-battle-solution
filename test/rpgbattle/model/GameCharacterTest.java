package rpgbattle.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameCharacterTest {

    private GameCharacter
        sally  = new GameCharacter("Sally", 100, 10, new Fireball(15, 7)),
        marvin = new GameCharacter("Marvin", 50, 3, new Sword(5, 10));

    @Test
    void fireballAttack() {
        assertEquals(
            "Sally hit Marvin with a fireball for 15 points of damage",
            sally.attack(marvin));
        assertEquals(3, sally.getEnergy());
        assertEquals(35, marvin.getHitPoints());
        assertEquals(100, sally.getHitPoints());
    }

    @Test
    void fireballAttackNotEnoughEnergy() {
        sally.useEnergy(5);
        assertEquals(5, sally.getEnergy());

        assertEquals(
            "Sally did not have enough energy for a fireball attack",
            sally.attack(marvin));
        assertEquals(5, sally.getEnergy());
        assertEquals(50, marvin.getHitPoints());
        assertEquals(100, sally.getHitPoints());
    }

    @Test
    void fireballAttackToZero() {
        marvin.takeDamage(49);
        assertEquals(1, marvin.getHitPoints());

        assertEquals(
            "Sally hit Marvin with a fireball for 15 points of damage",
            sally.attack(marvin));
        assertEquals(3, sally.getEnergy());
        assertEquals(0, marvin.getHitPoints());
        assertEquals(100, sally.getHitPoints());
    }

    @Test
    void swordAttack() {
        String message = marvin.attack(sally);
        assertTrue(
            message.matches("Marvin struck Sally with a sword for \\d+ points of damage"));
        assertTrue(sally.getHitPoints() <= 95);
        assertTrue(sally.getHitPoints() >= 90);
        assertEquals(50, marvin.getHitPoints());
    }


    @Test
    void swordAttackToZero() {
        sally.takeDamage(98);
        assertEquals(2, sally.getHitPoints());

        String message = marvin.attack(sally);
        assertTrue(
            message.matches("Marvin struck Sally with a sword for \\d+ points of damage"));
        assertEquals(0, sally.getHitPoints());
        assertEquals(50, marvin.getHitPoints());
    }
}
