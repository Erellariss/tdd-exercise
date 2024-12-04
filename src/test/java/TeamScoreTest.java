import org.example.score.TeamScore;
import org.example.score.exception.ErrorMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TeamScoreTest {

    private static final String TEAM_NAME = "teamName";

    @Test
    public void scoreShouldKeepTeamName() {
        var score = new TeamScore(TEAM_NAME);

        assertEquals(TEAM_NAME, score.getTeamName());
    }

    @Test
    public void scoreShouldNotAcceptNullName() {
        var exception = assertThrows(NullPointerException.class, () -> new TeamScore(null));
        assertEquals(ErrorMessages.TEAM_NAME_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    public void scoreShouldNotAcceptEmptyName() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new TeamScore(""));
        assertEquals(ErrorMessages.TEAM_NAME_CANNOT_BE_EMPTY_STRING, exception.getMessage());
    }

    @Test
    public void scoreShouldBeZeroOnCreation() {
        var given = new TeamScore(TEAM_NAME);

        assertEquals(0, given.getScore());
    }

    @Test
    public void shouldUpdateScore() {
        var score = new TeamScore(TEAM_NAME);

        score.updateScore(11);

        assertEquals(11, score.getScore());
    }

    @Test
    void shouldNotAllowToDecreaseScore() {
        var score = new TeamScore(TEAM_NAME);

        score.updateScore(11);
        var exception = assertThrows(IllegalArgumentException.class, () -> score.updateScore(1));
        assertEquals(ErrorMessages.SCORE_CANNOT_BE_LESS_THAN_EXISTING, exception.getMessage());
    }
}
