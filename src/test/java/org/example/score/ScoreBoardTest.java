package org.example.score;

import org.junit.jupiter.api.Test;

import static org.example.score.exception.ErrorMessages.TEAM_ALREADY_PLAYING;
import static org.example.score.exception.ErrorMessages.TEAM_CANNOT_PLAY_MATCH_WITH_ITSELF;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    private static final String TEAM_A = "teamA";
    private static final String TEAM_B = "teamB";
    private static final String TEAM_C = "teamC";

    @Test
    void newScoreBoardShouldBeEmpty() {
        var board = new ScoreBoard();
        assertTrue(board.getMatches().isEmpty());
    }

    @Test
    void shouldCreateNewMatches() {
        //given
        var board = new ScoreBoard();

        //when
        board.startMatch(TEAM_A, TEAM_B);
        var matches = board.getMatches();
        var newMatchInfo = matches.getFirst();
        
        //then
        assertNotNull(newMatchInfo);
        assertEquals(TEAM_A, newMatchInfo.getHomeTeamName());
        assertEquals(TEAM_B, newMatchInfo.getAwayTeamName());
        assertEquals(0, newMatchInfo.getAwayTeamScore());
        assertEquals(0, newMatchInfo.getAwayTeamScore());
        assertFalse(matches.isEmpty());
        assertEquals(1, matches.size());
    }

    @Test
    void shouldNotAllowCreateMatchWithTheTeamItself() {
        //given
        var board = new ScoreBoard();

        //when
        var exception = assertThrows(IllegalArgumentException.class, () -> board.startMatch(TEAM_A, TEAM_A));

        assertEquals(TEAM_CANNOT_PLAY_MATCH_WITH_ITSELF, exception.getMessage());
    }

    @Test
    void shouldNotAllowCreateMatchWithTheTeamOnBoardHome() {
        //given
        var board = new ScoreBoard();
        board.startMatch(TEAM_A, TEAM_B);

        //when
        var exception = assertThrows(IllegalArgumentException.class, () -> board.startMatch(TEAM_A, TEAM_C));

        assertEquals(TEAM_ALREADY_PLAYING, exception.getMessage());
    }

    @Test
    void shouldNotAllowCreateMatchWithTheTeamOnBoardAway() {
        //given
        var board = new ScoreBoard();
        board.startMatch(TEAM_A, TEAM_B);

        //when
        var exception = assertThrows(IllegalArgumentException.class, () -> board.startMatch(TEAM_C, TEAM_B));

        assertEquals(TEAM_ALREADY_PLAYING, exception.getMessage());
    }

    @Test
    void shouldAllowToFinishExistingMatch() {
        //given
        var board = new ScoreBoard();
        board.startMatch(TEAM_A, TEAM_B);

        board.finishMatch(TEAM_A, TEAM_B);

        assertTrue(board.getMatches().isEmpty());
    }
}
