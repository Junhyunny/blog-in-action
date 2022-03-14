package action.in.blog.rps.strategy;

import action.in.blog.rps.Hand;

public interface RpsStrategy {

    int versus(Hand otherHand);
}
