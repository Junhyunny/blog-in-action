package action.in.blog.rps;

import action.in.blog.rps.strategy.RpsStrategy;
import action.in.blog.rps.strategy.impl.NormalPaperStrategy;
import action.in.blog.rps.strategy.impl.NormalRockStrategy;
import action.in.blog.rps.strategy.impl.NormalScissorsStrategy;

public enum Hand {

    SCISSORS(new NormalScissorsStrategy()),
    PAPER(new NormalPaperStrategy()),
    ROCK(new NormalRockStrategy());

//    SCISSORS(new ExtendedScissorsStrategy()),
//    PAPER(new ExtendedPaperStrategy()),
//    ROCK(new ExtendedRockStrategy()),
//    LIZARD(new ExtendedLizardStrategy()),
//    SPOCK(new ExtendedSpockStrategy());

    private final RpsStrategy strategy;

    Hand(RpsStrategy strategy) {
        this.strategy = strategy;
    }

    public int versus(Hand otherHand) {
        return strategy.versus(otherHand);
    }
}
