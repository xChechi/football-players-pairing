package com.example.footballpairing.dto.match;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchUpdateRequest {

    @NotNull
    private int firstTeamId;

    @NotNull
    private int secondTeamId;

    @Past
    private LocalDate date;

    @NotNull
    private String regularScore;

    private String penaltyScore;
}
