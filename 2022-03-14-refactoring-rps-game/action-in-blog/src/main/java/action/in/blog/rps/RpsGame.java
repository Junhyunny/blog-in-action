package action.in.blog.rps;

public class RpsGame {
    public static String play(Hand player1, Hand player2) {
        int result = player1.versus(player2);
        if (result == 1) {
            return "PLAYER1";
        } else if (result == -1) {
            return "PLAYER2";
        }
        return "DRAW";
    }
}
