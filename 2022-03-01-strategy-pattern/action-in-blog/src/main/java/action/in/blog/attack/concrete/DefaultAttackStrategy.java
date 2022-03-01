package action.in.blog.attack.concrete;

import action.in.blog.GameCharacter;
import action.in.blog.attack.AttackStrategy;

public class DefaultAttackStrategy implements AttackStrategy {

    @Override
    public void attack(GameCharacter gameCharacter) {
        // 5 code lines to punch
        System.out.println("punch");
    }
}
