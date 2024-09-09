package com.example.footballpairing.dto.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerUpdateRequest {

    private String teamNumber;
    private String position;
    private String fullName;
    private int teamId;
}
