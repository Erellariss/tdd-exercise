import org.example.score.TeamScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreTest {

    private static final String TEAM_NAME = "teamName";

    @Test
    public void scoreShouldKeepTeamName() {
        var score = new TeamScore(TEAM_NAME);

        assertEquals(TEAM_NAME, score.getTeamName());
    }

    @Test
    public void scoreShouldNotAcceptNullName() {
        var exception = assertThrows(NullPointerException.class, () -> new TeamScore(null));
        assertEquals("Team name cannot be null!", exception.getMessage());
    }

    @Test
    public void scoreShouldNotAcceptEmptyName() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new TeamScore(""));
        assertEquals("Team name cannot be empty string!", exception.getMessage());
    }

    @Test
    public void scoreShouldBeZeroOnCreation() {
        var given = new TeamScore(TEAM_NAME);

        assertEquals(0, given.getScore());
    }
}
