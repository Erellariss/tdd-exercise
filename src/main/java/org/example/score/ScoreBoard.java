package org.example.score;

import org.example.score.exception.ErrorMessages;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.example.score.exception.ErrorMessages.TEAMS_ARE_NOT_IN_PLAY;
import static org.example.score.exception.ErrorMessages.TEAM_IS_NOT_IN_PLAY;

public class ScoreBoard {

    private final Map<String, MatchInfo> matchesMap = new HashMap<>();
    private final Map<String, Integer> teamsInPlay = new HashMap<>();
    private final AtomicLong matchesCounter = new AtomicLong();

    public void startMatch(String homeTeam, String awayTeam) {
        if (teamsInPlay.containsKey(homeTeam) || teamsInPlay.containsKey(awayTeam)) {
            throw new IllegalArgumentException(ErrorMessages.TEAM_ALREADY_PLAYING);
        }
        var matchInfo = new MatchInfo(homeTeam, awayTeam, matchesCounter.incrementAndGet());
        matchesMap.put(homeTeam + awayTeam, matchInfo);
        teamsInPlay.put(homeTeam, 0);
        teamsInPlay.put(awayTeam, 0);
    }

    public SortedSet<MatchInfo> getMatches() {
        var matches = matchesMap.values();
        var treeSet = new TreeSet<>(MatchInfo::compareTo);
        treeSet.addAll(matches);
        return Collections.unmodifiableSortedSet(treeSet);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        if (!teamsInPlay.containsKey(homeTeam)
                || !teamsInPlay.containsKey(awayTeam)
                || null == matchesMap.remove(homeTeam + awayTeam)) {
            throw new RuntimeException(TEAMS_ARE_NOT_IN_PLAY);
        }
        teamsInPlay.remove(homeTeam);
        teamsInPlay.remove(awayTeam);
    }

    public void updateScores(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        var matchInfo = matchesMap.get(homeTeam + awayTeam);
        matchInfo.updateScores(homeScore, awayScore);
        teamsInPlay.put(homeTeam, homeScore);
        teamsInPlay.put(awayTeam, awayScore);
    }

    public int getTeamScore(String teamName) {
        if (!teamsInPlay.containsKey(teamName)) {
            throw new IllegalArgumentException(TEAM_IS_NOT_IN_PLAY);
        }
        return teamsInPlay.get(teamName);
    }
}
