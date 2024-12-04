package org.example.score;

public class TeamScore {

    private final String teamName;

    public TeamScore(String teamName) {
        if (teamName == null) {
            throw new NullPointerException("Team name cannot be null!");
        }
        if (teamName.isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be empty string!");
        }
        this.teamName = teamName;
    }


    public String getTeamName() {
        return teamName;
    }

    public int getScore() {
        return 0;
    }
}
