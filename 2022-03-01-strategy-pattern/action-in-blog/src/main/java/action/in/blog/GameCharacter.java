package action.in.blog;

import action.in.blog.attack.AttackStrategy;
import action.in.blog.attack.concrete.DefaultAttackStrategy;

public class GameCharacter {

    // ... some fields to describe character attributes

    private final AttackStrategy defaultAttackStrategy = new DefaultAttackStrategy();

    private AttackStrategy weaponAttackStrategy;

    public void setWeaponAttackStrategy(AttackStrategy weaponAttackStrategy) {
        this.weaponAttackStrategy = weaponAttackStrategy;
    }

    public void attack() {
        if (weaponAttackStrategy == null) {
            defaultAttackStrategy.attack(this);
            return;
        }
        weaponAttackStrategy.attack(this);
    }
}
