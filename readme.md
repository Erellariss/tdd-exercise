# TDD Coding exercise

This is a TDD coding exercise.

# Requirements

1. Start a new match, assuming initial score 0 – 0 and adding it the scoreboard.
   This should capture following parameters:
   a. Home team
   b. Away team
2. Update score. This should receive a pair of absolute scores: home team score and away
   team score.
3. Finish match currently in progress. This removes a match from the scoreboard.
4. Get a summary of matches in progress ordered by their total score. The matches with the
   same total score will be returned ordered by the most recently started match in the
   scoreboard.

# Assumptions

- The same team cannot be in several matches
- Team cannot play with itself
- Int range is enough to keep team score
- Implementation is not thread safe

# How to run
```bash
./mvnw clean install
```
