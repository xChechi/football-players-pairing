package com.example.footballpairing.dto.match;

import com.example.footballpairing.entity.Team;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
