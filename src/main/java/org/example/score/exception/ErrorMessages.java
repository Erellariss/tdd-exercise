package org.example.score.exception;

public final class ErrorMessages {

    public static final String SCORE_CANNOT_BE_LESS_THAN_EXISTING = "Score cannot be less than existing one!";
    public static final String TEAM_NAME_CANNOT_BE_EMPTY_STRING = "Team name cannot be empty string!";
    public static final String TEAM_NAME_CANNOT_BE_NULL = "Team name cannot be null!";
    public static final String TEAM_CANNOT_PLAY_MATCH_WITH_ITSELF = "Team cannot play match with itself!";
    public static final String TEAM_ALREADY_PLAYING = "Team is already on board!";
    public static final String TEAMS_ARE_NOT_IN_PLAY = "Team pair has not started match!";
    public static final String TEAM_IS_NOT_IN_PLAY = "Requested Team is not existing on board!";

    private ErrorMessages() {}
}
