package com.example.footballpairing.utility;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ScoreParser {

    public String extractRegularScore(String score) {
        Pattern patternWithPenalties = Pattern.compile("(\\d+)?\\((\\d+)\\)-(\\d+)?\\((\\d+)\\)");
        Matcher matcherWithPenalties = patternWithPenalties.matcher(score);

        if (matcherWithPenalties.find()) {
            String firstTeamScore = matcherWithPenalties.group(1) != null ? matcherWithPenalties.group(1) : "0";
            String secondTeamScore = matcherWithPenalties.group(3) != null ? matcherWithPenalties.group(3) : "0";
            return firstTeamScore + "-" + secondTeamScore;
        }

        Pattern patternWithoutPenalties = Pattern.compile("(\\d+)-(\\d+)");
        Matcher matcherWithoutPenalties = patternWithoutPenalties.matcher(score);

        if (matcherWithoutPenalties.find()) {
            return matcherWithoutPenalties.group(1) + "-" + matcherWithoutPenalties.group(2);
        }
        return "";
    }

    public String extractPenaltyScore(String score) {
        Pattern pattern = Pattern.compile("\\d+\\((\\d+)\\)-\\d+\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(score);
        if (matcher.find()) {
            return matcher.group(1) + "-" + matcher.group(2);
        }
        return "";
    }
}
