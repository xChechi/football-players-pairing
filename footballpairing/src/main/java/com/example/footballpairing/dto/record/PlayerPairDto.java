package com.example.footballpairing.dto.record;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerPairDto {

    private String playerOneFullName;
    private String playerTwoFullName;
    private int totalPlayedTime;
    private List<MatchDetailDto> matchDetails = new ArrayList<>();

}