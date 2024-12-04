package org.example.score;

import org.example.score.exception.ErrorMessages;

import java.util.Comparator;

public class MatchInfo implements Comparable<MatchInfo> {

    private final TeamScore homeTeamScore;
    private final TeamScore awayTeamScore;

    public MatchInfo(String homeTeamName, String awayTeamName) {
        if (homeTeamName != null && homeTeamName.equals(awayTeamName)) {
            throw new IllegalArgumentException(ErrorMessages.TEAM_CANNOT_PLAY_MATCH_WITH_ITSELF);
        }
        this.homeTeamScore = new TeamScore(homeTeamName);
        this.awayTeamScore = new TeamScore(awayTeamName);
    }

    public long getHomeTeamScore() {
        return homeTeamScore.getScore();
    }

    public long getAwayTeamScore() {
        return awayTeamScore.getScore();
    }

    public String getHomeTeamName() {
        return homeTeamScore.getTeamName();
    }

    public String getAwayTeamName() {
        return awayTeamScore.getTeamName();
    }

    protected void updateScores(long homeScore, long awayScore) {
        this.homeTeamScore.updateScore(homeScore);
        this.awayTeamScore.updateScore(awayScore);
    }

    @Override
    public int compareTo(MatchInfo other) {
        return Comparator.<MatchInfo, Long>comparing(info -> info.awayTeamScore.getScore() + info.homeTeamScore.getScore(), Long::compareUnsigned)
                .compare(this, other);
    }
}
