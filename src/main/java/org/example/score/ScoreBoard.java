package org.example.score;

import org.example.score.exception.ErrorMessages;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.example.score.exception.ErrorMessages.TEAMS_ARE_NOT_IN_PLAY;

public class ScoreBoard {

    private final Map<String, MatchInfo> matchesMap = new HashMap<>();
    private final Set<String> teamsInPlay = new HashSet<>();
    private final AtomicLong matchesCounter = new AtomicLong();

    public void startMatch(String homeTeam, String awayTeam) {
        if (teamsInPlay.contains(homeTeam) || teamsInPlay.contains(awayTeam)) {
            throw new IllegalArgumentException(ErrorMessages.TEAM_ALREADY_PLAYING);
        }
        var matchInfo = new MatchInfo(homeTeam, awayTeam, matchesCounter.incrementAndGet());
        matchesMap.put(homeTeam + awayTeam, matchInfo);
        teamsInPlay.add(homeTeam);
        teamsInPlay.add(awayTeam);
    }

    public SortedSet<MatchInfo> getMatches() {
        var matches = matchesMap.values();
        var treeSet = new TreeSet<>(MatchInfo::compareTo);
        treeSet.addAll(matches);
        return Collections.unmodifiableSortedSet(treeSet);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        if (!teamsInPlay.contains(homeTeam)
                || !teamsInPlay.contains(awayTeam)
                || null == matchesMap.remove(homeTeam + awayTeam)) {
            throw new RuntimeException(TEAMS_ARE_NOT_IN_PLAY);
        }
        teamsInPlay.remove(homeTeam);
        teamsInPlay.remove(awayTeam);
    }

    public void updateScores(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        var matchInfo = matchesMap.get(homeTeam + awayTeam);
        matchInfo.updateScores(homeScore, awayScore);
    }
}
