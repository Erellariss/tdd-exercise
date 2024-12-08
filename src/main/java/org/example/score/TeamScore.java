package org.example.score;

import org.example.score.exception.ErrorMessages;

public class TeamScore {

    private final String teamName;
    private int score = 0;

    public TeamScore(String teamName) {
        if (teamName == null) {
            throw new NullPointerException(ErrorMessages.TEAM_NAME_CANNOT_BE_NULL);
        }
        if (teamName.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.TEAM_NAME_CANNOT_BE_EMPTY_STRING);
        }
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int score) {
        if (score < this.score) {
            throw new IllegalArgumentException(ErrorMessages.SCORE_CANNOT_BE_LESS_THAN_EXISTING);
        }
        this.score = score;
    }
}
