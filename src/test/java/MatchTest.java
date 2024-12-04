import org.example.score.MatchInfo;
import org.example.score.exception.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest {

    private static final String TEAM_A = "teamA";
    private static final String TEAM_B = "teamB";

    static Stream<Arguments> provideParametersAndExceptions() {
        return Stream.of(
                Arguments.of(null, TEAM_B, NullPointerException.class, ErrorMessages.TEAM_NAME_CANNOT_BE_NULL),
                Arguments.of(TEAM_A, null, NullPointerException.class, ErrorMessages.TEAM_NAME_CANNOT_BE_NULL),
                Arguments.of("", TEAM_B, IllegalArgumentException.class, ErrorMessages.TEAM_NAME_CANNOT_BE_EMPTY_STRING),
                Arguments.of(TEAM_A, "", IllegalArgumentException.class, ErrorMessages.TEAM_NAME_CANNOT_BE_EMPTY_STRING)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParametersAndExceptions")
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

        var homeScore = match.getHomeTeamScore();
        var awayScore = match.getAwayTeamScore();

        assertEquals(TEAM_A, homeScore.getTeamName());
        assertEquals(TEAM_B, awayScore.getTeamName());
    }

    @Test
    void shouldHaveZeroesAsInitialScores() {
        var match = new MatchInfo(TEAM_A, TEAM_B);

        var homeScore = match.getHomeTeamScore();
        var awayScore = match.getAwayTeamScore();

        assertEquals(0, homeScore.getScore());
        assertEquals(0, awayScore.getScore());
    }

}
