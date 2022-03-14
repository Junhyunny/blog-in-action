package action.in.blog.rps.strategy.impl;

import action.in.blog.rps.Hand;
import action.in.blog.rps.strategy.RpsStrategy;

public class NormalScissorsStrategy implements RpsStrategy {

    @Override
    public int versus(Hand otherHand) {
        if (otherHand.equals(Hand.PAPER)) {
            return 1;
        } else if (otherHand.equals(Hand.ROCK)) {
            return -1;
        }
        return 0;
    }
}
