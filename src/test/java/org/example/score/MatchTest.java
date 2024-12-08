package org.example.score;

import org.example.score.exception.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest {

    private static final String TEAM_A = "teamA";
    private static final String TEAM_B = "teamB";

    static Stream<Arguments> provideTeamNamesAndExpectedExceptions() {
        return Stream.of(
                Arguments.of(null, TEAM_B, NullPointerException.class, ErrorMessages.TEAM_NAME_CANNOT_BE_NULL),
                Arguments.of(TEAM_A, null, NullPointerException.class, ErrorMessages.TEAM_NAME_CANNOT_BE_NULL),
                Arguments.of("", TEAM_B, IllegalArgumentException.class, ErrorMessages.TEAM_NAME_CANNOT_BE_EMPTY_STRING),
                Arguments.of(TEAM_A, "", IllegalArgumentException.class, ErrorMessages.TEAM_NAME_CANNOT_BE_EMPTY_STRING)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTeamNamesAndExpectedExceptions")
    void shouldNotAllowCreateMatchWithWrongTeamNames(String teamHome,
                                                     String teamAway,
                                                     Class<? extends Exception> expectedException,
                                                     String expectedErrorMessage) {
        var exception = assertThrows(expectedException, () -> new MatchInfo(teamHome, teamAway));
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void shouldCreateMatchWithProperTeamNames() {
        var match = new MatchInfo(TEAM_A, TEAM_B);

        var homeTeam = match.getHomeTeamName();
        var awayTeam = match.getAwayTeamName();

        assertEquals(TEAM_A, homeTeam);
        assertEquals(TEAM_B, awayTeam);
    }

    @Test
    void shouldHaveZeroesAsInitialScores() {
        var match = new MatchInfo(TEAM_A, TEAM_B);

        var homeScore = match.getHomeTeamScore();
        var awayScore = match.getAwayTeamScore();

        assertEquals(0, homeScore);
        assertEquals(0, awayScore);
    }


    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 0",
            "0, 1",
            "1, 1",
            "12314, 0"
    })
    void shouldUpdateScores(int a, int b) {
        var match = new MatchInfo(TEAM_A, TEAM_B);

        match.updateScores(a, b);

        assertEquals(a, match.getHomeTeamScore());
        assertEquals(b, match.getAwayTeamScore());
    }

    @ParameterizedTest
    @CsvSource({
            "0, -1",
            "-1, 0",
            "-1, -1"
    })
    void shouldNotUpdateScores(int a, int b) {
        var match = new MatchInfo(TEAM_A, TEAM_B);

        var exception = assertThrows(IllegalArgumentException.class, () -> match.updateScores(a, b));
        assertEquals(ErrorMessages.SCORE_CANNOT_BE_LESS_THAN_EXISTING, exception.getMessage());
    }
}
