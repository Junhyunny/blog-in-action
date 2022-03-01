package action.in.blog.attack.concrete;

import action.in.blog.GameCharacter;
import action.in.blog.attack.AttackStrategy;

public class KnifeAttackStrategy implements AttackStrategy {

    @Override
    public void attack(GameCharacter gameCharacter) {
        // 10 code lines to stab motion
        System.out.println("stab with a knife");
    }
}
