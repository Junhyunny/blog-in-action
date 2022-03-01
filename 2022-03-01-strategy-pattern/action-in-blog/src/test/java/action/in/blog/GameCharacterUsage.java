package action.in.blog;

import action.in.blog.attack.AttackStrategy;
import action.in.blog.attack.concrete.KnifeAttackStrategy;
import action.in.blog.attack.concrete.SwordAttackStrategy;

// enum for management relationship between weapon and strategy
enum WeaponType {

    KNIFE(new KnifeAttackStrategy()),
    SWORD(new SwordAttackStrategy());

    private final AttackStrategy attackStrategy;

    WeaponType(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }
}

public class GameCharacterUsage {

    static GameCharacter gameCharacter = new GameCharacter();

    public static void weaponPickUpEventHandler(String type) {

        // legacy code
        // gameCharacter.setWeaponType(type);

        // new code
        AttackStrategy attackStrategy = null;
        try {
            WeaponType weaponType = WeaponType.valueOf(type);
            attackStrategy = weaponType.getAttackStrategy();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        gameCharacter.setWeaponAttackStrategy(attackStrategy);
    }

    public static void main(String[] args) {

        weaponPickUpEventHandler("KNIFE");
        gameCharacter.attack();

        weaponPickUpEventHandler("SWORD");
        gameCharacter.attack();

        weaponPickUpEventHandler(null);
        gameCharacter.attack();
    }
}
