package com.example.footballpairing.dto.match;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequest {

    @NotNull
    private int firstTeamId;

    @NotNull
    private int secondTeamId;

    @NotBlank
    private String date;

    @NotNull
    private String regularScore;

    private String penaltyScore;
}
