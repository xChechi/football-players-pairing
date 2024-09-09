package com.example.footballpairing.utility;

import com.example.footballpairing.entity.Match;
import org.springframework.stereotype.Component;

@Component
public class MatchDescriptionFormatter {

    public String formatMatchDescription(Match match) {
        String team1Name = match.getFirstTeam().getName();
        String team2Name = match.getSecondTeam().getName();
        return team1Name + " - " + team2Name;
    }
}
