package org.example.score;

import org.example.score.exception.ErrorMessages;

import java.util.*;

public class ScoreBoard {

    private final Map<String, MatchInfo> matches = new HashMap<>();
    private final Set<String> teamsInPlay = new HashSet<>();

    public void startMatch(String homeTeam, String awayTeam) {
        if (teamsInPlay.contains(homeTeam) || teamsInPlay.contains(awayTeam)) {
            throw new IllegalArgumentException(ErrorMessages.TEAM_ALREADY_PLAYING);
        }
        matches.put(homeTeam + awayTeam, new MatchInfo(homeTeam, awayTeam));
        teamsInPlay.add(homeTeam);
        teamsInPlay.add(awayTeam);
    }

    public SortedSet<MatchInfo> getMatches() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(matches.values()));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        matches.remove(homeTeam + awayTeam);
    }
}
