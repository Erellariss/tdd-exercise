package org.example.score;

public class MatchInfo {

    private final TeamScore homeTeamScore;
    private final TeamScore awayTeamScore;

    public MatchInfo(String homeTeamName, String awayTeamName) {
        this.homeTeamScore = new TeamScore(homeTeamName);
        this.awayTeamScore = new TeamScore(awayTeamName);
    }

    public TeamScore getHomeTeamScore() {
        return homeTeamScore;
    }

    public TeamScore getAwayTeamScore() {
        return awayTeamScore;
    }
}
