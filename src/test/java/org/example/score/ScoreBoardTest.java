package org.example.score;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.example.score.exception.ErrorMessages.*;
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

    @Test
    void shouldNotAllowToFinishNonExistingMatch() {
        var board = new ScoreBoard();

        var runtimeException = assertThrows(RuntimeException.class, () -> board.finishMatch(TEAM_A, TEAM_B));
        assertEquals(TEAMS_ARE_NOT_IN_PLAY, runtimeException.getMessage());
    }

    @Test
    void shouldAllowToUpdateMatchScores() {
        //given
        var board = new ScoreBoard();
        board.startMatch(TEAM_A, TEAM_B);

        board.updateScores(TEAM_A, TEAM_B, 2, 3);
        var matches = board.getMatches();
        var matchInfo = matches.getFirst();

        assertEquals(1, matches.size());
        assertEquals(2, matchInfo.getHomeTeamScore());
        assertEquals(3, matchInfo.getAwayTeamScore());
    }

    @Test
    void shouldAllowToUpdateMatchScoresMultipleTimes() {
        //given
        var board = new ScoreBoard();
        board.startMatch(TEAM_A, TEAM_B);

        board.updateScores(TEAM_A, TEAM_B, 2, 3);
        board.updateScores(TEAM_A, TEAM_B, 3, 3);
        board.updateScores(TEAM_A, TEAM_B, 5, 10);
        var matches = board.getMatches();
        var matchInfo = matches.getFirst();

        assertEquals(1, matches.size());
        assertEquals(5, matchInfo.getHomeTeamScore());
        assertEquals(10, matchInfo.getAwayTeamScore());
    }

    @Test
    void shouldOrderMatchesByRequirements() {
        var board = new ScoreBoard();
        var givenTeams = List.of(
                MatchInfo.of("Mexico", "Canada", 0, 5),
                MatchInfo.of("Spain", "Brazil", 10, 2),
                MatchInfo.of("Germany", "France", 2, 2),
                MatchInfo.of("Uruguay", "Italy", 6, 6),
                MatchInfo.of("Argentina", "Australia", 3, 1)
        );
        for (MatchInfo givenTeam : givenTeams) {
            board.startMatch(givenTeam.getHomeTeamName(), givenTeam.getAwayTeamName());
        }
        for (MatchInfo givenTeam : givenTeams) {
            board.updateScores(givenTeam.getHomeTeamName(), givenTeam.getAwayTeamName(), givenTeam.getHomeTeamScore(), givenTeam.getAwayTeamScore());
        }

        var expectedOrderedTeams = List.of(
                MatchInfo.of("Uruguay", "Italy", 6, 6),
                MatchInfo.of("Spain", "Brazil", 10, 2),
                MatchInfo.of("Mexico", "Canada", 0, 5),
                MatchInfo.of("Argentina", "Australia", 3, 1),
                MatchInfo.of("Germany", "France", 2, 2)
        );

        var matches = new ArrayList<>(board.getMatches());
        assertEquals(5, matches.size());
        for (int i = 0; i < matches.size(); i++) {
            assertMatchInfo(expectedOrderedTeams.get(i), matches.get(i));
        }
    }

    private static void assertMatchInfo(MatchInfo expected, MatchInfo given) {
        assertEquals(expected.getHomeTeamName(), given.getHomeTeamName());
        assertEquals(expected.getAwayTeamName(), given.getAwayTeamName());
        assertEquals(expected.getHomeTeamScore(), given.getHomeTeamScore());
        assertEquals(expected.getAwayTeamScore(), given.getAwayTeamScore());
    }

    @Test
    void shouldRespectMatchOrderByCreationOrderIfSumOfScoreIsTheSame() {
        var board = new ScoreBoard();
        board.startMatch("a", "b");
        board.startMatch("c", "d");
        board.startMatch("e", "f");

        board.updateScores("a", "b", 2, 0);
        board.updateScores("c", "d", 1, 1);
        board.updateScores("e", "f", 0, 2);

        var matches = board.getMatches();
        assertEquals(3, matches.size());
        var iterator = matches.iterator();
        var prev = iterator.next();
        while (iterator.hasNext()) {
            var next = iterator.next();
            assertTrue(next.getOrderNo() < prev.getOrderNo());
            prev = next;
        }
    }

    @Test
    void shouldRetrieveSpecificTeamScore() {
        var board = new ScoreBoard();
        board.startMatch("a", "b");
        board.updateScores("a", "b", 2, 5);

        assertEquals(2, board.getTeamScore("a"));
        assertEquals(5, board.getTeamScore("b"));
    }

    @Test
    void shouldRetrieveSpecificTeamScoreWithoutMatchUpdate() {
        var board = new ScoreBoard();
        board.startMatch("a", "b");

        assertEquals(0, board.getTeamScore("a"));
        assertEquals(0, board.getTeamScore("b"));
    }

    @Test
    void shouldNotRetrieveByNonExistentTeam() {
        var board = new ScoreBoard();
        board.startMatch("a", "b");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> board.getTeamScore("c"));
        assertEquals(TEAM_IS_NOT_IN_PLAY, exception.getMessage());
    }

}
