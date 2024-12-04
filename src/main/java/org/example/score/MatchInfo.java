package org.example.score;

public class MatchInfo {

    private final TeamScore homeTeamScore;
    private final TeamScore awayTeamScore;

    public MatchInfo(String homeTeamName, String awayTeamName) {
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

    public void updateScores(long homeScore, long awayScore) {
        this.homeTeamScore.updateScore(homeScore);
        this.awayTeamScore.updateScore(awayScore);
    }
}
