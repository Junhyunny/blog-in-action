package action.in.blog.rps;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RpsGameTests {

    @Test
    public void rockVsScissors_play_winnerIsPlayer1() {
        String winner = RpsGame.play(Hand.ROCK, Hand.SCISSORS);
        assertThat(winner, equalTo("PLAYER1"));
    }

    @Test
    public void rockVsPaper_play_winnerPlayer2() {
        String winner = RpsGame.play(Hand.ROCK, Hand.PAPER);
        assertThat(winner, equalTo("PLAYER2"));
    }

    @Test
    public void rockVsRock_play_draw() {
        String winner = RpsGame.play(Hand.ROCK, Hand.ROCK);
        assertThat(winner, equalTo("DRAW"));
    }

    @Test
    public void paperVsRock_play_winnerIsPlayer1() {
        String winner = RpsGame.play(Hand.PAPER, Hand.ROCK);
        assertThat(winner, equalTo("PLAYER1"));
    }

    @Test
    public void paperVsScissors_play_winnerPlayer2() {
        String winner = RpsGame.play(Hand.PAPER, Hand.SCISSORS);
        assertThat(winner, equalTo("PLAYER2"));
    }

    @Test
    public void paperVsPaper_play_draw() {
        String winner = RpsGame.play(Hand.PAPER, Hand.PAPER);
        assertThat(winner, equalTo("DRAW"));
    }

    @Test
    public void scissorsVsPaper_play_winnerIsPlayer1() {
        String winner = RpsGame.play(Hand.SCISSORS, Hand.PAPER);
        assertThat(winner, equalTo("PLAYER1"));
    }

    @Test
    public void scissorVsRock_play_winnerPlayer2() {
        String winner = RpsGame.play(Hand.SCISSORS, Hand.ROCK);
        assertThat(winner, equalTo("PLAYER2"));
    }

    @Test
    public void scissorVsScissors_play_draw() {
        String winner = RpsGame.play(Hand.SCISSORS, Hand.SCISSORS);
        assertThat(winner, equalTo("DRAW"));
    }
}
