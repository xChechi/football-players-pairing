package com.example.footballpairing.dto.match;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponse {

    private int id;
    private String firstTeamName;
    private String secondTeamName;
    private LocalDate date;
    private String regularScore;
    private String penaltyScore;
}
