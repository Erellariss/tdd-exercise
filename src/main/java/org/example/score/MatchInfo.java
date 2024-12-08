package org.example.score;

import org.example.score.exception.ErrorMessages;

import java.util.Comparator;

public class MatchInfo implements Comparable<MatchInfo> {

    private final TeamScore homeTeamScore;
    private final TeamScore awayTeamScore;
    private final long orderNo;

    public MatchInfo(String homeTeamName, String awayTeamName) {
        this(homeTeamName, awayTeamName, 0);
    }

    public MatchInfo(String homeTeamName, String awayTeamName, long orderNo) {
        if (homeTeamName != null && homeTeamName.equals(awayTeamName)) {
            throw new IllegalArgumentException(ErrorMessages.TEAM_CANNOT_PLAY_MATCH_WITH_ITSELF);
        }
        this.homeTeamScore = new TeamScore(homeTeamName);
        this.awayTeamScore = new TeamScore(awayTeamName);
        this.orderNo = orderNo;
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

    protected long getOrderNo() {
        return orderNo;
    }

    protected static MatchInfo of(String homeTeamName, String awayTeamName, long homeScore, long awayScore) {
        var matchInfo = new MatchInfo(homeTeamName, awayTeamName);
        matchInfo.updateScores(homeScore, awayScore);
        return matchInfo;
    }

    @Override
    public int compareTo(MatchInfo other) {
        return Comparator.<MatchInfo, Long>comparing(info -> info.awayTeamScore.getScore() + info.homeTeamScore.getScore(), Long::compareUnsigned)
                .thenComparing(MatchInfo::getOrderNo, Long::compareTo)
                .reversed()
                .compare(this, other);
    }
}
